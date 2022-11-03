package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fidelity.smallchange.model.Trade;

@SpringBootTest
class TradeOrderDaoMapperTest {

	@Autowired
	TradeExecutionMapper tradeExecutionMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllTrades() {
		List<Trade> trade= tradeExecutionMapper.getTradesByClient("1234");
		assertEquals(1,trade.size());
	}

}
