package com.fidelity.smallchange.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.ClientPreferences;

@SpringBootTest
@Transactional
class ClientPreferencesDaoMyBatisImplTest {
	
	@Autowired
	ClientPreferencesDao dao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void testGetClientPreferences() {
		ClientPreferences preferences = new ClientPreferences("1235", "Savings", 5, 3, 4);
		
		ClientPreferences result = dao.getClientPreferences("1235");
		assertThat(result, is(equalTo(preferences)));
		
	}
	
	@Test
	void testInsertClientPreferences() {
		String clientId = "1234";
		ClientPreferences preferences = new ClientPreferences(clientId, "Savings", 5, 3, 4);
		
		assertThat(0, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "preferences", "clientid = " + clientId))));
		int rows = dao.insertClientPreferences(preferences);
		assertThat(rows, equalTo(1));
		
		assertThat(1, is(equalTo(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "preferences", "clientid = " + clientId))));
	}
	
	@Test
	void testUpdateClientPreferences() {
		String clientId = "1235";
		ClientPreferences originalPreferences = dao.getClientPreferences(clientId);
		originalPreferences.setInvestmentPurpose("College");
		
		int rows = dao.updateClientPreferences(originalPreferences);
		ClientPreferences updatedPreferences = dao.getClientPreferences(clientId);
		
		assertThat(rows, equalTo(1));
		assertThat(updatedPreferences, is(equalTo(originalPreferences)));
	}

}
