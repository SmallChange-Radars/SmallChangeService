package com.fidelity.smallchange.integration;

import java.math.BigDecimal;
import java.util.ArrayList;

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

import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.Country;

@SpringBootTest
@Transactional
class ClientDaoMyBatisImplTest {
	
	@Autowired
	ClientDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetClientById() {
		ClientDB client = new ClientDB("1234", "aadrs@gmail.com", "19900101", Country.US, "123456", new ArrayList<ClientIdentification>(), null, null, new BigDecimal("12345.45"), "USD", null);
		
		ClientDB result = dao.getClientById("1234");
		assertThat(result, is(equalTo(client)));
	}
	
	@Test
	void testInsertClient() {
		String ClientId = "0139";
		
		assertThat(0, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", "clientid = " + ClientId))));

		ClientDB client = new ClientDB("0139", "aadrs@gmail.com", "19900101", Country.US, "123456", new ArrayList<ClientIdentification>(), "", "", new BigDecimal("12345.45"), "USD", "");
		int rows = dao.insertClient(client);
		
		assertThat(rows, is(equalTo(1)));
		assertThat(1, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", "clientid = " + ClientId))));
	}
	
	@Test
	void testCheckClientByEmail() {
		String email = "aadrs@gmail.com";
		int result = dao.checkClientByEmail(email);
		
		assertThat(result, is(equalTo(1)));
	}
	
	@Test 
	void TestGetClientWalletById() {
		ClientDB client = new ClientDB(null, null, null, null, null, null, null, null, new BigDecimal("12345.45"), "USD", null);
		ClientDB result = dao.getClientWalletById("1234");
		assertThat(result, is(equalTo(client)));
	}
	
	@Test
	void TestUpdateClientWallet() {
		String clientId = "1234";
		
		ClientDB originalClient = dao.getClientWalletById(clientId);
		originalClient.setWallet(new BigDecimal("1000.55"));
		
		int rows = dao.updateClientWallet(new BigDecimal("1000.55"), clientId);
		ClientDB updatedClient = dao.getClientWalletById(clientId);
		
		assertThat(rows, is(equalTo(1)));
		assertThat(updatedClient,is(equalTo(originalClient)));
	}

}
