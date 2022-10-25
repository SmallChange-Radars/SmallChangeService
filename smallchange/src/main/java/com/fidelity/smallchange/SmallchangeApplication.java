package com.fidelity.smallchange;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Trade;

@SpringBootApplication
@MapperScan(basePackages = { "com.fidelity.smallchange.integration.mapper" }, annotationClass = Mapper.class)
public class SmallchangeApplication implements CommandLineRunner {
	
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private TradeExecutionMapper tradeExecutionMapper;
	
	@Override
    public void run(String...args) throws Exception {
		
    }

	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
