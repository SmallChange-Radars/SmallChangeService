package com.fidelity.smallchange.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.fidelity.smallchange.SmallchangeApplication;
import com.fidelity.smallchange.integration.ClientDao;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.Country;
import com.fidelity.smallchange.model.Trade;

@SpringBootTest(classes = SmallchangeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class TradeControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	private ClientDao dao;
	
	String token;
	String clientId = "290988199";
	
	@BeforeEach
	void setUp() throws Exception {
		JSONParser jsonParser = new JSONParser();

		ClientDB client = new ClientDB("", "a000007@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"itsasecret101", BigDecimal.TEN, "USD", "ROLE_CLIENT");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ClientDB> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/auth/signin", HttpMethod.POST, request,
				String.class);
		Object obj = jsonParser.parse(response.getBody());
		JSONObject toke = (JSONObject) obj;
		token = toke.get("accessToken").toString();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetTradeActivity() {
		String url = "/api/tradeActivity?q=RRC&_page=1&_limit=10&_sort=cashValue&_order=DESC&_category=B";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<Trade[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Trade[].class);
		assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
	}
	
	@Test
	void testGetTradeActivity_NovalueInDB() {
		String url = "/api/tradeActivity?q=QWER&_page=1&_limit=10&_sort=cashValue&_order=DESC&_category=B";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<Trade[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Trade[].class);
		assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
	}

}
