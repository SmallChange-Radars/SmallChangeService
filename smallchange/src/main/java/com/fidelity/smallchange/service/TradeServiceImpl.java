package com.fidelity.smallchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Token;
import com.fidelity.smallchange.model.Trade;

@Service
public class TradeServiceImpl implements TradeService {
	
	@Autowired
	private TradeOrderDao dao;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PortfolioMapper portfolioMapper;
	
	@Autowired
	private FMTSRestClient fmtsRestClient;
	
	public List<Trade> getTradeActivityByClientId(String clientId) throws Exception{
		
		try {
			return dao.getTradeActivityByClient(clientId);
			
		}catch(Exception e) {
			throw new Exception("Error while connecting to DB");
		}
	}

	@Override
	public boolean tradeExecution(Order order, String clientId) throws Exception {
		try {
			System.out.println("hello");
			Token token = clientService.getToken(clientId);
			order.setClientId(clientId);
			order.setToken(token.getToken());
			System.out.println(order.toString());
			dao.insertOrder(order);
			Trade trade = fmtsRestClient.tradeExecution(order);
			System.out.println(trade.toString());
			if(trade==null) {
				return false;
			}
			dao.insertTrade(trade);
//			Portfolio portfolio = new Portfolio(order.getClientId(), order.getInstrumentId(), order.getQuantity(), trade.getCashValue());
//			portfolioMapper.insertPortfolio(portfolio);
			return true;
			
		}catch(Exception e) {
			throw new Exception("Error while executing trade",e);
		}
	}
	
}
