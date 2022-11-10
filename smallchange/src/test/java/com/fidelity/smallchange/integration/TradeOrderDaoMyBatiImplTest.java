package com.fidelity.smallchange.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.ietf.jgss.Oid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Trade;

@SpringBootTest
@Transactional
class TradeOrderDaoMyBatiImplTest {

	@Autowired
	TradeOrderDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	Order order;
	Trade trade;
	
	@BeforeEach
	void setUp() throws Exception {	
		order=new Order("AA", 20, new BigDecimal(110.77), "B", "290988199","5ff604e8-731b-47e2-a33e-3c034877c1cc",null);
		trade = new Trade("fowqhfdytcu-1d60ovdi71t-d3eiwrj92ww","AA",20,"B", new BigDecimal(110.77), new BigDecimal(2573.19),"2022-11-09T09:39:55",order);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetTradeActivityByClient() {
		List<Trade> trades = dao.getTradeActivityByClient("290988199", "%%", "%%", "timestamp", "desc", 0, 10);
		
		System.out.println(trades.get(0).toString());
		assertEquals(10, trades.size());
		
	}
	
	@Test
	void testInsertOrder() {
		assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"orderinstrument","instrumentid = 'AA'"));
		dao.insertOrder(order);
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"orderinstrument","instrumentid = 'AA'"));
	}
	
	@Test
	void testInsertTrade() {
		assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"trade","instrumentid = 'AA'"));
		dao.insertOrder(order);
		dao.insertTrade(trade);
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"trade","instrumentid = 'AA'"));
	}
	
	@Test
	void testGetWalletAmount() {
		BigDecimal walletAmount = dao.getWalletAmount("290988199");
		assertEquals(new BigDecimal(1868.754).setScale(3, BigDecimal.ROUND_HALF_UP), walletAmount);
	}
	
	@Test
	void testGetInstrumentQuantity() {
		Portfolio portfolio  = dao.getInstrumentQuantity("290988199", "HST");
		assertEquals(1920, portfolio.getQuantity());
	}
	
	@Test
	void testGetTotalTradesByClient() {
		int totalTrades = dao.totalTradesByClientId("290988199", "%%", "%%");
		assertEquals(157, totalTrades);
	}
	

}
