package com.fidelity.smallchange.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.Portfolio;

@SpringBootTest
class PortfolioServiceImplTest {
	
	@Autowired
	private PortfolioService dao;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test 
	void testGetAllPortfolios() { 
		List<Portfolio> p = dao.getAllPortfolios(); 
		assertEquals(169, p.size()); 
	}
	
	@Test
	void testGetPortfolioByClientId() {
		List<Portfolio> p = dao.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
	}
	
	@Test
	@Transactional
	void testInsertPortfolio() {
		List<Portfolio> p = dao.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
		Portfolio newPort = new Portfolio("744385865", "T67890", 70, new BigDecimal("670.89"));
		dao.insertPortfolio(newPort);
		int newSize = dao.getPortfolioByClientId("744385865").size();
		assertEquals(67, newSize);
	}
	
	@Test
	void testCheckAvailableQuantity() {
		assertTrue(dao.checkAvailableQuantity("744385865", "MNST", 30));
	}
	
	@Test
	void testCheckAvailableQuantityReturnsFalse() {
		assertFalse(dao.checkAvailableQuantity("744385865", "MNST", 300000));
	}
}
