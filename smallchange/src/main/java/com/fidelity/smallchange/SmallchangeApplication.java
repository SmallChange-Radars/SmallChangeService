package com.fidelity.smallchange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.TradeOrderDaoImpl;
import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Trade;

@SpringBootApplication
public class SmallchangeApplication implements CommandLineRunner {

	@Autowired
	TradeOrderDao mapper;
	@Override
    public void run(String...args) throws Exception {
		
		System.out.println(mapper.getTradesByClient("1234").get(0).toString());
		
	}

	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
