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
	private PortfolioMapper dao;

	@Test
	void testGetAllPortfolios() {
		List<Portfolio> p = dao.getAllPortfolios();
		assertEquals(6, p.size());
	}
	
	@Test
	void testGetPortfolioByClientId() {
		List<Portfolio> p = dao.getPortfolioByClientId("1238");
		assertEquals(3, p.size());
	}
	
	@Test
	@Transactional
	void testInsertPortfolio() {
		List<Portfolio> p = dao.getPortfolioByClientId("1238");
		assertEquals(3, p.size());
		Portfolio newPort = new Portfolio("1238", "T67890", 70, new BigDecimal("670.89"));
		dao.insertPortfolio(newPort);
		int newSize = dao.getPortfolioByClientId("1238").size();
		assertEquals(4, newSize);
	}
	
	@Test
	@Transactional
	void testDeletePortfolioByClientIdAndInstrumentId() {
		List<Portfolio> p = dao.getPortfolioByClientId("1238");
		assertEquals(3, p.size());
		dao.deletePortfolioByClientIdAndInstrumentId("1238", "Q123");
		int newSize = dao.getPortfolioByClientId("1238").size();
		assertEquals(2, newSize);
	}
	
	@Test
	void testGetPortfolioByClientIdandInstrumentID() {
		Portfolio portfolio = dao.getPortfolioByClientIdAndInstrumentId("1234", "Q456");
		if(portfolio==null)
		System.out.println("hello");
		else {
			System.out.println(portfolio.toString());
		}
	}

}
