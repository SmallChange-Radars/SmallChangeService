package com.fidelity.smallchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

@Service
public class TradeServiceImpl implements TradeService {
	
	@Autowired
	private TradeOrderDao dao;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PortfolioMapper portfolioMapper;
	
	public List<Trade> getTradeActivityByClientId(String clientId) throws Exception{
		
		try {
			return dao.getTradeActivityByClient(clientId);
			
		}catch(Exception e) {
			throw new Exception("Error while connecting to DB");
		}
	}

	@Override
	public void tradeExecution(Order order) throws Exception {
		try {
			
			
		}catch(Exception e) {
			
		}
		
	}
	
}
