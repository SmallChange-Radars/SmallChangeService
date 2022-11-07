package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;

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
		if(tradeExecutionMapper.getInstrumentQuantity("123544", "qwqwe")==null);
		System.out.println("Hello");
	}

}
