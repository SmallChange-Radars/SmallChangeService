package com.fidelity.smallchange.integration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Instrument;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Price;
import com.fidelity.smallchange.model.Trade;

@Repository("FMTSClientRestImpl")
public class FMTSRestClient {

	String url = "http://localhost:3000/fmts";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	public Client clientVerification(Client client) throws JsonProcessingException, HttpClientErrorException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Client> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange(url + "/client", HttpMethod.POST, request,
				String.class);

		// from analysing the code, their isn't really a possibility that 404 is
		// returned...

		// automatically throws HttpClientErrorException if 4xx though idk what to do
		// abt it, so assuming 406 and 404 both means invalid client it should be fine

		Client verified = (Client) restJSONMapper(new Client(), response, new TypeReference<Client>() {
		});
		return verified;
	}

	public Trade tradeExecution(Order order) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Order> request = new HttpEntity<>(order, headers);

		ResponseEntity<String> response = restTemplate.exchange(url + "/trades/trade", HttpMethod.POST, request,
				String.class);

		// TODO:
		// Trade doesnt return 409 for invalid trade object instead returns a null
		// object
		Trade verified = (Trade) restJSONMapper(new Trade(), response, new TypeReference<Trade>() {
		});
		return verified;
	}

	public List<Price> getPrices(String categoryId) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Client> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
				url + "/trades/prices" + (categoryId.equals("") ? categoryId : "/" + categoryId), HttpMethod.GET,
				request, String.class);

		List<Price> verified = castList(
				restJSONMapper(new ArrayList<Price>(), response, new TypeReference<List<Price>>() {
				}), Price.class);
		return verified;
	}

	public List<Instrument> getInstruments(String categoryId) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Client> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
				url + "/trades/instruments" + (categoryId.equals("") ? categoryId : "/" + categoryId), HttpMethod.GET,
				request, String.class);

		List<Instrument> verified = castList(
				restJSONMapper(new ArrayList<Instrument>(), response, new TypeReference<List<Instrument>>() {
				}), Instrument.class);
		return verified;
	}

	public <T> List<T> castList(Object obj, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		if (obj instanceof List<?>) {
			for (Object o : (List<?>) obj) {
				result.add(clazz.cast(o));
			}
			return result;
		}
		return null;
	}

	public <T> Object restJSONMapper(Object respObj, ResponseEntity<String> response, TypeReference<T> typeReference)
			throws JsonProcessingException {
		// TODO:
		// this parsing error only means it couldnt be mapped properly so need to see
		// how to handle this...
		// mostly should return 5xx
		respObj = objectMapper.readValue(response.getBody(), typeReference);
		return respObj;
	}

}
