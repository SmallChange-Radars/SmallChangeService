package com.fidelity.smallchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.TradeOrderDaoImpl;
import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Trade;

@SpringBootApplication
public class SmallchangeApplication{

	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
