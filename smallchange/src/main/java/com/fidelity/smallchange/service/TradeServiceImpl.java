package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
			Token token = clientService.getToken(clientId);
			String orderId = UUID.randomUUID().toString();
			order.setClientId(clientId);
			order.setToken(token.getToken());
			order.setOrderId(orderId);
			// verify order (balance and all)
			if(verifyTrade(order)) {
				dao.insertOrder(order);
				Trade trade = fmtsRestClient.tradeExecution(order);
				if(trade==null) {
					return false;
				}
				trade.getOrder().setOrderId(order.getOrderId());
				dao.insertTrade(trade);
			}
			else {
				return false;
			}
			return true;
			
		}catch(Exception e) {
			throw new Exception("Error while executing trade",e);
		}
	}
	
	public boolean verifyTrade(Order order) {
		if(order.getDirection()=="S") {
			int availableQuantity = dao.getInstrumentQuantity(order.getClientId(), order.getInstrumentId());
			if(availableQuantity < order.getQuantity()) {
				return false;
			}
			return true;
		}
		if(order.getDirection() == "B") {
			BigDecimal walletAmount = dao.getWalletAmount(order.getClientId());
			if(walletAmount.compareTo(order.getTargetPrice()) <0) {
				return false;
			}
			return true;
		}
		return true;
	}
	
}
