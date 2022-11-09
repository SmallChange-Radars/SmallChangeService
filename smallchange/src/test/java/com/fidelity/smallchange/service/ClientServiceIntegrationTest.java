package com.fidelity.smallchange.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.ClientPreferences;
import com.fidelity.smallchange.model.Country;

@SpringBootTest
@Transactional
class ClientServiceIntegrationTest {
	
	@Autowired
	ClientService service;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void basicSanityTest() {
		assertNotNull(service);
	}
	
	@Test
	void testGetClientByClientId() {
		String clientId = "1234";
		ClientDB client = new ClientDB(clientId, "aadrs@gmail.com", "19900101", Country.US, "123456", new ArrayList<ClientIdentification>(), null, null, new BigDecimal("12345.45"), "USD", null);
		
		ClientDB result = service.getClientByClientId(clientId);
		assertThat(result, is(equalTo(client)));
	}
	
	@Test
	void testInsertClient_NullIdentification() {
		String clientId = "0139";
		ClientDB client = new ClientDB(clientId, "aadrs@gmail.com", "19900101", Country.US, "123456", null, "", "", new BigDecimal("12345.45"), "USD", "");
		Client c = new Client();
		c.setClientId(clientId);
		c.setToken("Token");
		
		assertThat(0, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", "clientid = " + clientId))));
		int rows = service.insertClient(client, c);
		
		assertThat(rows, equalTo(1));
		assertThat(1, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", "clientid = " + clientId))));
	}
	
	@Test
	void testInsertClient_NotNullIdentification() {
		String clientId = "0139";
		List<ClientIdentification> identification = new ArrayList<>();
		identification.add(new ClientIdentification("SSN", "SSNVal1"));
		ClientDB client = new ClientDB(clientId, "aadrs@gmail.com", "19900101", Country.US, "123456", identification, "", "", new BigDecimal("12345.45"), "USD", "");
		Client c = new Client();
		c.setClientId(clientId);
		c.setToken("Token");
		
		assertThat(0, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", "clientid = " + clientId))));
		int rows = service.insertClient(client, c);
		
		assertThat(rows, equalTo(1));
		assertThat(1, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", "clientid = " + clientId))));
	}
	
	@Test
	void testCheckClientByEmail() {
		String email = "aadrs@gmail.com";
	
		boolean result = service.checkClientByEmail(email);
		assertThat(result, equalTo(true));
	}
	
	@Test
	void testGetClientWalletById() {
		ClientDB client = new ClientDB(null, null, null, null, null, null, null, null, new BigDecimal("12345.45"), "USD", null);
		ClientDB result = service.getClientWalletByClientId("1234");
		assertThat(result, is(equalTo(client)));
	}
	
	@Test
	void testUpdateClientWallet() {
		String clientId = "1235";
		ClientDB client = loadClientWalletFromDb(clientId);
		
		BigDecimal newWallet = client.getWallet().add(BigDecimal.ONE);
		client.setWallet(newWallet);
		
		int rows = service.updateClientWallet(clientId, newWallet);
		ClientDB updatedClient = loadClientWalletFromDb(clientId);
		
		assertThat(rows, equalTo(1));
		assertThat(updatedClient, is(equalTo(client)));
	}
	
	@Test
	void testGetClientPreferencesById() {
		ClientPreferences preferences = new ClientPreferences("1235", "Savings", 5, 3, 4);
		
		ClientPreferences result = service.getClientPreferencesById("1235");
		assertThat(result, is(equalTo(preferences)));
	}
	
	@Test
	void testInsertClientPreferences() {
		String clientId = "1234";
		ClientPreferences preferences = new ClientPreferences(clientId, "Savings", 5, 3, 4);
		
		assertThat(0, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "preferences", "clientid = " + clientId))));
		int rows = service.insertClientPreferences(preferences);
		
		assertThat(rows, equalTo(1));
		assertThat(1, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "preferences", "clientid = " + clientId))));
	}
	
	@Test
	void testUpdateClientPreferences() {
		String clientId = "1235";
		ClientPreferences preferences = loadClientPreferencesFromDb(clientId);
		preferences.setInvestmentPurpose("College");
		
		int rows = service.updateClientPreferences(preferences);
		ClientPreferences updatedPreferences = loadClientPreferencesFromDb(clientId);
		
		assertThat(rows, equalTo(1));
		assertThat(updatedPreferences, is(equalTo(preferences)));
		
	}
	
	/*****Utility Functions******/
	
	private ClientDB loadClientFromDb(String clientId) {
		String sql = "select * from client where clientId = " + clientId;
		String sql2 = "select * from clientidentification where clientid = " + clientId;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
		new ClientDB(rs.getString("clientId"), rs.getString("email"), rs.getString("dob"), Country.of(rs.getString("country")), 
				   rs.getString("postalCode"), jdbcTemplate.query(sql2, (rs2, rowNum2) -> new ClientIdentification(rs2.getString("type"), rs2.getString("value"))), null, rs.getString("password"),
				   rs.getBigDecimal("wallet"), rs.getString("walletCurrency"), rs.getString("role")));	
	}
	
	private ClientDB loadClientWalletFromDb(String clientId) {
		String sql = "select * from client where clientId = " + clientId;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
		new ClientDB(null, null, null, null, null, null , null, null, rs.getBigDecimal("wallet"), rs.getString("walletCurrency"), null));	
	}
	
	private ClientPreferences loadClientPreferencesFromDb(String clientId) {
		String sql = "select * from preferences where clientId = " + clientId;
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
		new ClientPreferences(rs.getString("clientId"), rs.getString("investmentPurpose"), rs.getInt("riskTolerance"),
			rs.getInt("incomeCategory"), rs.getInt("lengthOfInvestment")));
	}

}
