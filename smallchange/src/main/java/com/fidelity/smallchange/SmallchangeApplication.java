package com.fidelity.smallchange;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.TradeOrderDaoImpl;
import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

@SpringBootApplication
public class SmallchangeApplication{

	
	
	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
