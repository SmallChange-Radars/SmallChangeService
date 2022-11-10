package com.fidelity.smallchange.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.Portfolio;

@SpringBootTest
class PortfolioDaoMyBatisImplTest {


	@Autowired
	private PortfolioDao dao;

	
	@Test 
	void testGetAllPortfolios() { 
		List<Portfolio> p = dao.getAllPortfolios(); assertEquals(175, p.size()); 
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
	@Transactional
	void testDeletePortfolioByClientIdAndInstrumentId() {
		List<Portfolio> p = dao.getPortfolioByClientId("744385865");
		assertEquals(66, p.size());
		dao.deletePortfolioByClientIdAndInstrumentId("744385865", "MNST");
		int newSize = dao.getPortfolioByClientId("744385865").size();
		assertEquals(65, newSize);
	}
	
	@Test
	void testGetPortfolioByClientIdandInstrumentID() {
		Portfolio portfolio = dao.getPortfolioByClientIdAndInstrumentId("744385865", "MNST");
		Portfolio expectedPortfolio = new Portfolio("744385865", "MNST", 911, new BigDecimal("57037.62"));
		assertEquals(expectedPortfolio, portfolio);
	}
	
	@Test
	void testGetPortfolioSummaryValue() {
		BigDecimal summaryValue = dao.getPortfolioSummary("744385865");
		assertEquals(new BigDecimal("1986855.91"), summaryValue);
	}
	
	@Test
	@Transactional
	void testUpdatePortfolio() {
		Portfolio newPort = new Portfolio("744385865", "MNST", 70, new BigDecimal("670.89"));
		dao.updatePortfolio(newPort);
		assertEquals(newPort, dao.getPortfolioByClientIdAndInstrumentId("744385865", "MNST"));
	}

}
