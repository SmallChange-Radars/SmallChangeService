package com.fidelity.smallchange;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.Country;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Price;

@SpringBootTest(classes = SmallchangeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class Generate {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	List<Order> order;
	String token, token1;

	@BeforeEach
	void setUp() throws Exception {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("src/main/resources/orderdata.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray orderList = (JSONArray) obj;

			order = objectMapper.readValue(orderList.toJSONString(), new TypeReference<List<Order>>() {
			});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// run both clients separately with different range in for loop
		ClientDB client = new ClientDB("", "a000007@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"itsasecret101", BigDecimal.TEN, "USD", "ROLE_CLIENT");
		ClientDB client2 = new ClientDB("", "dave101@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"FSE3fmr", BigDecimal.TEN, "USD", "ROLE_CLIENT");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ClientDB> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/auth/signin", HttpMethod.POST, request,
				String.class);
		Object obj = jsonParser.parse(response.getBody());

		JSONObject toke = (JSONObject) obj;
		token = toke.get("accessToken").toString();

		request = new HttpEntity<>(client2, headers);
		response = restTemplate.exchange("/api/auth/signin", HttpMethod.POST, request, String.class);
		obj = jsonParser.parse(response.getBody());

		toke = (JSONObject) obj;
		token1 = toke.get("accessToken").toString();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testVerifyOrderSize() {
		assertThat(order.size(), equalTo(2016));
	}

	@Test
	void testPopulateOrdersForUser1() {
		// not all trade will be successful due to not yet bought and wallet stuff
		Instant start = Instant.now();
		int successCount = 0, buyRequests = 0, sellRequests = 0;
		for (int i = 1008; i < 2016; i++) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + token);

			HttpEntity<Order> request = new HttpEntity<>(order.get(i), headers);
			ResponseEntity<String> response = restTemplate.exchange("/api/tradeExecution", HttpMethod.POST, request,
					String.class);
			if (response.getStatusCodeValue() == 200) {
				successCount++;
				if (order.get(i).getDirection().equals("B")) {
					buyRequests++;
				} else {
					sellRequests++;
				}
			}
		}
		Instant end = Instant.now();
		System.out.println("Successful trades made for Client1: \t" + successCount + "\t in "
				+ Duration.between(start, end).getSeconds() + " seconds.");
		System.out.println("Successful Buys: \t" + buyRequests);
		System.out.println("Successful Sells: \t" + sellRequests);
		assertThat(successCount, equalTo(157));
		assertThat(buyRequests, equalTo(125));
		assertThat(sellRequests, equalTo(32));
	}

	@Test
	void testPopulateOrdersForUser2() {
		// not all trade will be successful due to not yet bought and wallet stuff
		Instant start = Instant.now();
		int successCount = 0, buyRequests = 0, sellRequests = 0;
		for (int i = 0; i < 1008; i++) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + token1);

			HttpEntity<Order> request = new HttpEntity<>(order.get(i), headers);
			ResponseEntity<String> response = restTemplate.exchange("/api/tradeExecution", HttpMethod.POST, request,
					String.class);
			if (response.getStatusCodeValue() == 200) {
				successCount++;
				if (order.get(i).getDirection().equals("B")) {
					buyRequests++;
				} else {
					sellRequests++;
				}
			}
		}
		Instant end = Instant.now();
		System.out.println("Successful trades made for Client2: \t" + successCount + "\t in "
				+ Duration.between(start, end).getSeconds() + " seconds.");
		System.out.println("Successful Buys: \t" + buyRequests);
		System.out.println("Successful Sells: \t" + sellRequests);
		assertThat(successCount, equalTo(95));
		assertThat(buyRequests, equalTo(78));
		assertThat(sellRequests, equalTo(17));
	}
}
