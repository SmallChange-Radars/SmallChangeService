package com.fidelity.smallchange.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.smallchange.model.Portfolio;

class PortfolioDaoQueryTest {
	
	PortfolioDao dao;
	SimpleDataSource dataSource;
	
	Portfolio p1234;

	@BeforeEach
	void setUp() throws Exception {
		dataSource = new SimpleDataSource();
		
		dao = new PortfolioDaoOracleImpl(dataSource);
	}

	@AfterEach
	void tearDown() throws Exception {
		dataSource.shutdown();
	}

	@Test
	void testGetPortfolioByClientId() {
		List<Portfolio> p = dao.getPortfolioById("1238");
		assertEquals(3, p.size());
	}

}
