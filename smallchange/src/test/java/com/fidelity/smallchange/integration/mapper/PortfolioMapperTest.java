package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.Portfolio;

@SpringBootTest
class PortfolioMapperTest {
	
	@Autowired
	private PortfolioMapper mapper;

	@Test
	void testGetAllPortfolios() {
		List<Portfolio> p = mapper.getAllPortfolios();
		assertEquals(175, p.size());
	}
	
	@Test
	void testGetPortfolioByClientId() {
		List<Portfolio> p = mapper.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
	}
	
	@Test
	@Transactional
	void testInsertPortfolio() {
		List<Portfolio> p = mapper.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
		mapper.deletePortfolioByClientIdAndInstrumentId("744385865", "MNST");
		int newSize = mapper.getPortfolioByClientId("744385865").size();
		assertEquals(65, newSize);
	}
	
	@Test
	@Transactional
	void testDeletePortfolioByClientIdAndInstrumentId() {
		List<Portfolio> p = mapper.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
		mapper.deletePortfolioByClientIdAndInstrumentId("744385865", "MNST");
		int newSize = mapper.getPortfolioByClientId("744385865").size();
		assertEquals(65, newSize);
	}
	
	@Test
	void testGetPortfolioByClientIdandInstrumentID() {
		Portfolio portfolio = mapper.getPortfolioByClientIdAndInstrumentId("744385865", "MNST");
		Portfolio expectedPortfolio = new Portfolio("744385865", "MNST", 911, new BigDecimal("57037.62"));
		assertEquals(expectedPortfolio, portfolio);
	}
	
	@Test
	@Transactional
	void testUpdatePortfolio() {
		Portfolio newPort = new Portfolio("744385865", "MNST", 70, new BigDecimal("670.89"));
		mapper.updatePortfolio("744385865", "MNST", 70, new BigDecimal("670.89"));
		assertEquals(newPort, mapper.getPortfolioByClientIdAndInstrumentId("744385865", "MNST"));
	}

}
