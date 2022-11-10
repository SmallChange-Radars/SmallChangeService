package com.fidelity.smallchange.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fidelity.smallchange.SmallchangeApplication;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.Country;

@SpringBootTest(classes = SmallchangeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class InstructionControllerTest {
	String token;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	void setUp() throws Exception {
		JSONParser jsonParser = new JSONParser();
		ClientDB client = new ClientDB("", "dave101@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"FSE3fmr", BigDecimal.TEN, "USD", "ROLE_CLIENT");

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
	void testGetInstrumentPrices() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<ClientDB> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/instruments-prices", HttpMethod.GET, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(response.getHeaders().containsKey("Access-Control-Expose-Headers"), equalTo(true));
		assertThat(response.getHeaders().get("X-Total-Count").toString(), equalTo("[518]"));
	}

	@Test
	void testGetInstrumentPricesWithQuery() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<ClientDB> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/instruments-prices?q=JP", HttpMethod.GET, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(response.getHeaders().containsKey("Access-Control-Expose-Headers"), equalTo(true));
		assertThat(response.getHeaders().get("Access-Control-Expose-Headers").toString(), equalTo("[X-Total-Count]"));
		assertThat(response.getHeaders().get("X-Total-Count").toString(), equalTo("[3]"));
	}

	@Test
	void testGetInstrumentPricesWithPagination() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<ClientDB> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/instruments-prices?_page=1&_limit=3", HttpMethod.GET, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(response.getHeaders().containsKey("Access-Control-Expose-Headers"), equalTo(true));
		assertThat(response.getHeaders().get("Access-Control-Expose-Headers").toString(), equalTo("[X-Total-Count]"));
		assertThat(response.getHeaders().get("X-Total-Count").toString(), equalTo("[518]"));
	}
	@Test
	void testGetInstrumentPricesWithCategoryID() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<ClientDB> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/instruments-prices?categoryId=cd", HttpMethod.GET, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(response.getHeaders().containsKey("Access-Control-Expose-Headers"), equalTo(true));
		assertThat(response.getHeaders().get("Access-Control-Expose-Headers").toString(), equalTo("[X-Total-Count]"));
		assertThat(response.getHeaders().get("X-Total-Count").toString(), equalTo("[1]"));
	}
	@Test
	void testGetInstrumentPricesWithAllFields() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<ClientDB> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/instruments-prices?q=govt&_page=1&_limit=3&categoryId=govt&_sort=askPrice&_order=desc", HttpMethod.GET, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(response.getHeaders().containsKey("Access-Control-Expose-Headers"), equalTo(true));
		assertThat(response.getHeaders().get("Access-Control-Expose-Headers").toString(), equalTo("[X-Total-Count]"));
		assertThat(response.getHeaders().get("X-Total-Count").toString(), equalTo("[8]"));
	}
}
