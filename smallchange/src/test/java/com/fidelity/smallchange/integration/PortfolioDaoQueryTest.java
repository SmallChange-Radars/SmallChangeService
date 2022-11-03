package com.fidelity.smallchange.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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
		
		dao = new PortfolioDaoMyBatisImpl(dataSource);
	}

	@AfterEach
	void tearDown() throws Exception {
		dataSource.shutdown();
	}

	@Test
	void testGetAllPortfolio() {
		List<Portfolio> p = dao.getAllPortfolios();
		assertEquals(6, p.size());
		Portfolio portfolio = new Portfolio("1238", "T67894", 91, new BigDecimal("10657.32"));
		assertTrue(p.contains(portfolio));
	}
	
	@Test
	void testGetPortfolioByClientId() {
		List<Portfolio> p = dao.getPortfolioByClientId("1238");
		assertEquals(3, p.size());
	}

}
