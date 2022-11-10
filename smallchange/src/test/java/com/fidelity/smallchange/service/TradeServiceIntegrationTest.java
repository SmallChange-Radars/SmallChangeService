package com.fidelity.smallchange.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

@SpringBootTest
@Transactional
class TradeServiceIntegrationTest {

	@Autowired
	TradeService tradeService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	Order order;
	Trade trade;

	@BeforeEach
	void setUp() throws Exception {
		order = new Order("RRC", 2, new BigDecimal(12.82), "B", "290988199", "5ff604e8-731b-47e2-a33e-3c034877c1cc",
				null);
		trade = new Trade("fowqhfdytcu-1d60ovdi71t-d3eiwrj92ww", "AA", 20, "B", new BigDecimal(12.82),
				new BigDecimal(2573.19), "2022-11-09T09:39:55", order);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetTradeActivityByClientId() throws Exception {
		List<Trade> trades = tradeService.getTradeActivityByClientId("290988199", "", "", 1, 10, "timestamp", "desc");
		assertEquals(10, trades.size());
	}

	@Test
	void testGetTradeActivityByClientId_Exception() {
		assertThrows(Exception.class, () -> {
			tradeService.getTradeActivityByClientId(null, null, null, 0, 0, null, null);
		});
	}

	@Test
	void testGetTotalTradesByClientId() throws Exception {
		int totalTrades = tradeService.totalTradesByClientId("290988199", "", "");
		assertEquals(157, totalTrades);
	}

	@Test
	void testGetToalTradesByClientId_Exception() {
		assertThrows(Exception.class, () -> {
			tradeService.totalTradesByClientId(null, null, null);
		});
	}

	@Test
	void testTradeExecution_Buy() throws Exception {

		boolean ans = tradeService.tradeExecution(order, "290988199");
		assertEquals(true, ans);

	}

	@Test
	void testTradeExecution_Sell() throws Exception {
		order.setDirection("S");

		boolean ans = tradeService.tradeExecution(order, "290988199");
		assertEquals(true, ans);

	}

	@Test
	void testTradeExecution_FMTSRejects() throws Exception {
		order.setInstrumentId("AAAA");

		boolean ans = tradeService.tradeExecution(order, "290988199");
		assertEquals(false, ans);
	}
	
	@Test
	void testTradeExecution_Exception() throws Exception {
		assertThrows(Exception.class, ()->{
			tradeService.tradeExecution(null, null);
		});
		
	}
}
