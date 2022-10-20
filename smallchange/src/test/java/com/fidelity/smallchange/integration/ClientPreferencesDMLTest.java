package com.fidelity.smallchange.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class ClientPreferencesDMLTest {
	JdbcTemplate jdbcTemplate;
	DbTestUtils dbTestUtils;
	ClientPreferencesDao dao;
	SimpleDataSource dataSource;
	Connection connection;
	TransactionManager transactionManager;

	@BeforeEach
	void setUp() throws Exception {
		dataSource = new SimpleDataSource();
		connection = dataSource.getConnection();
		transactionManager = new TransactionManager(dataSource);

		transactionManager.startTransaction();

		dbTestUtils = new DbTestUtils(connection);
		jdbcTemplate = dbTestUtils.initJdbcTemplate();

		dao = new ClientPreferencesDaoOracleImpl(dataSource);
	}

	@AfterEach
	void tearDown() throws Exception {
		transactionManager.rollbackTransaction();
		dataSource.shutdown();
	}

	@Test
	void testQueryByID() {
		
	}

}
