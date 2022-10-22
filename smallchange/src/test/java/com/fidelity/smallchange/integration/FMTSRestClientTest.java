package com.fidelity.smallchange.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Instrument;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Price;
import com.fidelity.smallchange.model.Trade;

@SpringBootTest
class FMTSRestClientTest {

	@Autowired
	private FMTSRestClient a;

	Client client1, client2, client1WithId, clientFromServer;
	
	Order order;

	String client, token, clients;

	@BeforeEach
	void setUp() throws Exception {
		client = "{\"clientId\":470861775,\"email\":\"dd@gmail.com\",\"dateOfBirth\":null,\"country\":null,\"postalCode\":null,\"clientIdentification\":null,\"token\":\"\"}";
		clients = "[{\"clientId\":470861775,\"email\":\"dd@gmail.com\",\"dateOfBirth\":null,\"country\":null,\"postalCode\":null,\"clientIdentification\":null,\"token\":470738319}]";
		client1 = new Client("", "dd@gmail.com", null, null, null, null, "");
		client1WithId = new Client("470861775", "dd@gmail.com", null, null, null, null, "");
		
		order = new Order("N123456", 2, BigDecimal.valueOf(104), "S", "470861775", "PQR",token);

		// Empty Email is incorrect details
		client2 = new Client("", "", null, null, null, null, "");

		clientFromServer = a.clientVerification(client1WithId);
		token = clientFromServer.getToken();
		
		order = new Order("N123456", 2, BigDecimal.valueOf(104), "S", "470861775", "PQR",token);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void mapStringToObjectWithCorrectClass() throws JsonProcessingException {
		ResponseEntity<String> testClient = new ResponseEntity<>(client, HttpStatus.ACCEPTED);

		Client verified = (Client) a.restJSONMapper(new Client(), testClient, new TypeReference<Client>() {
		});

		// checks if response has correct value
		assertEquals(verified, client1WithId);
	}

	@Test
	void mapStringToObjectsWithCorrectClass() throws JsonProcessingException {
		ResponseEntity<String> testClient = new ResponseEntity<>(clients, HttpStatus.ACCEPTED);

		List<Client> verified = a
				.castList(a.restJSONMapper(new ArrayList<Client>(), testClient, new TypeReference<List<Client>>() {
				}), Client.class);

		// checks if response has correct value
		assertEquals(verified.size(), 1);
	}

	@Test
	void mapStringToObjectWithIncorrectClass() {
		ResponseEntity<String> testClient = new ResponseEntity<>("somethingrandom", HttpStatus.ACCEPTED);

		JsonProcessingException ex = assertThrows(JsonProcessingException.class, () -> {
			a.restJSONMapper(new Client(), testClient, new TypeReference<Client>() {
			});
		});

		assert (ex.getMessage().contains("Unrecognized token 'somethingrandom': was expecting"));
	}

	@Test
	void getClientVerifiedWithCorrectCredentialstest() throws JsonProcessingException {
		Client client = a.clientVerification(client1);
		assertEquals(client.getEmail(), client1.getEmail());
	}

	@Test
	void get406WithIncorrectCredentialstest() {
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			a.clientVerification(client2);
		});
		assertEquals("406 Not Acceptable: \"n/a\"", ex.getMessage(), "Should return this error msg when invalid email");
	}
	
	@Test
	void executeTradeWithCorrectCredentials() throws JsonProcessingException {
		Trade trade = a.tradeExecution(order);
		assertEquals(trade.getQuantity(),2);
	}
	
	@Test
	void executeTradeWithIncorrectCredentials() throws JsonProcessingException {
		order.setToken("");
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			a.tradeExecution(order);
		});
		assertEquals("406 Not Acceptable: [no body]", ex.getMessage(), "Should return this error msg when invalid token");
	}
	
	@Test
	void executeTradeWithWrongPrice() throws JsonProcessingException {
		order.setTargetPrice(BigDecimal.TEN);
		
		//wrong price will return trade. 
		assertNull(a.tradeExecution(order));
	}

	@Test
	void getAllPrices() throws JsonProcessingException {
		List<Price> prices = a.getPrices("");
		assertEquals(prices.get(11).getInstrument().getCategoryId(), "STOCK");
	}

	@Test
	void getPricesByCategory() throws JsonProcessingException {
		List<Price> prices = a.getPrices("CD");
		assertEquals(prices.get(0).getInstrument().getCategoryId(), "CD");
	}

	@Test
	void getAllInstruments() throws JsonProcessingException {
		List<Instrument> instruments = a.getInstruments("");
		assertEquals(instruments.get(0).getCategoryId(), "STOCK");
	}

	@Test
	void getInstrumentsByCategory() throws JsonProcessingException {
		List<Instrument> instruments = a.getInstruments("STOCK");
		assertEquals(instruments.get(0).getCategoryId(), "STOCK");
	}
}
