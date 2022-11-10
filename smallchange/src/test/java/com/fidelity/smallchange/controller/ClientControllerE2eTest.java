package com.fidelity.smallchange.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.fidelity.smallchange.SmallchangeApplication;
import com.fidelity.smallchange.integration.ClientDao;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.ClientPreferences;
import com.fidelity.smallchange.model.Country;

@SpringBootTest(classes = SmallchangeApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
class ClientControllerE2eTest {

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
	void testGetClient() {
		String url = "/api/client/clientInfo";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<ClientDB> response = restTemplate.exchange(url, HttpMethod.GET, request, ClientDB.class);
		
		assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
		
		//Since Wallet Amount keeps changing
		BigDecimal amount = dao.getClientWalletById(clientId).getWallet();
		ClientDB client = new ClientDB("290988199", "a000007@gmail.com", "19260818", Country.of("US"), "90210", new ArrayList<ClientIdentification>(), null, null, amount, "USD", null);
		assertThat(response.getBody(),is(equalTo(client)));
	}
	
	@Test
	void testGetWallet() {
		String url = "/api/client/wallet";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<ClientDB> response = restTemplate.exchange(url, HttpMethod.GET, request, ClientDB.class);
		
		assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
		
		//Since Wallet Amount keeps changing
		BigDecimal amount = dao.getClientWalletById(clientId).getWallet();
		
		ClientDB client = new ClientDB(null, null, null, null, null, null, null, null, amount, "USD", null);
		assertThat(response.getBody(),is(equalTo(client)));
	}
	
//	@Test 
//	@Transactional
//	void testGetWallet_ThrowsException() {
//		JdbcTestUtils.dropTables(jdbcTemplate, "preferences");
//		JdbcTestUtils.dropTables(jdbcTemplate, "clientidentification");
//		JdbcTestUtils.dropTables(jdbcTemplate, "fmtstoken");
//		JdbcTestUtils.dropTables(jdbcTemplate, "trade");
//		JdbcTestUtils.dropTables(jdbcTemplate, "portfolio");
//		JdbcTestUtils.dropTables(jdbcTemplate, "orderInstrument");
//		JdbcTestUtils.dropTables(jdbcTemplate, "client");
//		String url = "/api/client/wallet";
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Content-Type", "application/json");
//		headers.set("Authorization", "Bearer " + token);
//		HttpEntity<?> request = new HttpEntity<>(headers);
//		
//		ResponseEntity<ClientDB> response = restTemplate.exchange(url, HttpMethod.GET, request, ClientDB.class);
//		assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
//	}
	
	@Test
	void testGetPreferences_NoPreferences() {
		String url = "/api/client/preferences";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<ClientPreferences> response = restTemplate.exchange(url, HttpMethod.GET, request, ClientPreferences.class);
		
		assertThat(response.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
		
		ClientPreferences preferences = null;
		assertThat(response.getBody(),is(equalTo(preferences)));
	}

}
