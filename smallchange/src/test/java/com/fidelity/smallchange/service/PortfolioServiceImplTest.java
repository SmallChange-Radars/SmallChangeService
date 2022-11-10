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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.model.ClientPortfolio;
import com.fidelity.smallchange.model.Portfolio;

@SpringBootTest
class PortfolioSserviceImplTest {
	
	@Autowired
	private PortfolioService service;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test 
	void testGetAllPortfolios() { 
		List<Portfolio> p = service.getAllPortfolios(); 
		assertEquals(169, p.size()); 
	}
	
	@Test
	void testGetPortfolioByClientId() {
		List<Portfolio> p = service.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
	}
	
	@Test
	@Transactional
	void testInsertPortfolio() {
		List<Portfolio> p = service.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
		Portfolio newPort = new Portfolio("744385865", "T67890", 70, new BigDecimal("670.89"));
		service.insertPortfolio(newPort);
		int newSize = service.getPortfolioByClientId("744385865").size();
		assertEquals(67, newSize);
	}
	
	@Test
	void testCheckAvailableQuantity() {
		assertTrue(service.checkAvailableQuantity("744385865", "MNST", 30));
	}
	
	@Test
	void testCheckAvailableQuantityReturnsFalse() {
		assertFalse(service.checkAvailableQuantity("744385865", "MNST", 300000));
	}
	
	@Test
    void testGetPortfolio() throws JsonProcessingException {
        Pair<List<ClientPortfolio>, List<BigDecimal>> pair =
                service.getClientPortfolio("744385865");
        assertEquals(66, pair.getFirst().size());
        assertEquals(new BigDecimal("1986855.91"), pair.getSecond().get(0));
        assertEquals(new BigDecimal("-19671.86"), pair.getSecond().get(1));
    }
}
