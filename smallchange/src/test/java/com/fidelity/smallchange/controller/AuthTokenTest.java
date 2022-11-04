package com.fidelity.smallchange.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.util.UUID;

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
class AuthTokenTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testClientCreationFailure() {
		ClientDB client = new ClientDB("", "d@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"itsasecret", BigDecimal.TEN, "USD", "ROLE_CLIENT");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ClientDB> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/auth/signup", HttpMethod.POST, request,
				String.class);
		assertThat(response.getBody(), equalTo("{\"message\":\"Error: Email is already in use!\"}"));
		assertThat(response.getStatusCodeValue(), equalTo(400));
	}

	@Test
	void testClientCreationSuccess() {
		ClientDB client = new ClientDB("",
				String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)
						+ "@gmail.com",
				"19260818", Country.of("US"), "90210", null, null, "itsasecret", BigDecimal.TEN, "USD", "ROLE_CLIENT");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ClientDB> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/auth/signup", HttpMethod.POST, request,
				String.class);
		assertThat(response.getBody(), equalTo("{\"message\":\"User registered successfully!\"}"));
		assertThat(response.getStatusCodeValue(), equalTo(200));
	}

	@Test
	void testClientSigninFailure() {
		ClientDB client = new ClientDB("", "d@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"itsasecre", BigDecimal.TEN, "USD", "ROLE_CLIENT");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ClientDB> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/auth/signin", HttpMethod.POST, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(401));
	}

	@Test
	void testClientSigninSuccess() {
		ClientDB client = new ClientDB("", "d@gmail.com", "19260818", Country.of("US"), "90210", null, null,
				"itsasecret", BigDecimal.TEN, "USD", "ROLE_CLIENT");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ClientDB> request = new HttpEntity<>(client, headers);
		ResponseEntity<String> response = restTemplate.exchange("/api/auth/signin", HttpMethod.POST, request,
				String.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
	}

}
