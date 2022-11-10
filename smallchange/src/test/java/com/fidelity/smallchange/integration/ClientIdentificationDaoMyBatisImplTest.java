package com.fidelity.smallchange.integration;

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

import com.fidelity.smallchange.model.ClientIdentification;

@SpringBootTest
@Transactional
class ClientIdentificationDaoMyBatisImplTest {

	@Autowired
	ClientIdentificationDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetClientIdentification() {
		List<ClientIdentification> identification = new ArrayList<>();
		identification.add(new ClientIdentification("SSN", "SSNVal1"));
		
		List<ClientIdentification> result = dao.getClientIdentification("1235");
		assertThat(result, is(equalTo(identification)));
		
	}
	
	@Test
	void testInsertClientIdentification() {
		String clientId = "1234";
		
		assertThat(0, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "clientIdentification", "clientid = " + clientId))));
		int rows = dao.insertClientIdentification("SSN", "SsnValue", clientId);
		assertThat(rows, equalTo(1));
		
		assertThat(1, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "clientIdentification", "clientid = " + clientId))));
	}
	
	@Test
	void testUpdateClientIdentification() {
		String clientId = "1235";
		List<ClientIdentification> originalIdentification = dao.getClientIdentification(clientId);
		originalIdentification.get(0).setValue("DifferentSSNValue");
		
		int rows = dao.updateClientIdentification("SSN", "DifferentSSNValue", clientId);
		List<ClientIdentification> updatedIdentification = dao.getClientIdentification(clientId);
		
		assertThat(rows, equalTo(1));
		assertThat(updatedIdentification, is(equalTo(originalIdentification)));
	}

}
