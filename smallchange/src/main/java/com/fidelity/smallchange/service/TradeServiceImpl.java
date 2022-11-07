package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.PortfolioDao;
import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Token;
import com.fidelity.smallchange.model.Trade;

import oracle.net.aso.f;

@Service
public class TradeServiceImpl implements TradeService {
	
	@Autowired
	private TradeOrderDao dao;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PortfolioDao portfolioDao;
	
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
				aggregateInPortfolio(trade);
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
		Portfolio portfolio;
		if(order.getDirection().compareTo("S")==0) {
			portfolio=dao.getInstrumentQuantity(order.getClientId(), order.getInstrumentId());
			if(portfolio!=null) {
				int availableQuantity=portfolio.getQuantity();
				if(availableQuantity>=order.getQuantity()) {
					return true;
				}else {
					
					return false;
				}
			}
			else {
				
				return false;
			}
		}
		if(order.getDirection().compareTo("B")==0) {
			BigDecimal walletAmount = dao.getWalletAmount(order.getClientId());
			if(walletAmount.compareTo(order.getTargetPrice()) <0) {
				return false;
			}
			return true;
		}
		return true;
	}
	
	public void aggregateInPortfolio(Trade trade) throws Exception {
		Portfolio portfolio=null;
		try {
			portfolio = portfolioDao.getPortfolioByClientIdAndInstrumentId(trade.getOrder().getClientId(), trade.getInstrumentId());
			if(portfolio == null) {
				portfolio = new Portfolio(trade.getOrder().getClientId(),trade.getInstrumentId(),trade.getQuantity(),trade.getCashValue());
				portfolioDao.insertPortfolio(portfolio);
				BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
				walletAmount=walletAmount.subtract(trade.getOrder().getTargetPrice());
				clientService.updateClientWallet(trade.getOrder().getClientId(), walletAmount);
			}
			else {
				
				
				if(trade.getDirection().compareTo("B") ==0) {
					
					BigDecimal originalValue = portfolio.getValue();
					originalValue=originalValue.add(trade.getCashValue());
					
					portfolio.setValue(originalValue);
					portfolio.setQuantity(portfolio.getQuantity()+trade.getQuantity());
					portfolioDao.updatePortfolio(portfolio);
					BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
					walletAmount=walletAmount.subtract(trade.getOrder().getTargetPrice());
					clientService.updateClientWallet(trade.getOrder().getClientId(), walletAmount);
					
				}else {
					BigDecimal originalValue = portfolio.getValue();
					originalValue=originalValue.subtract(trade.getCashValue());
					portfolio.setValue(originalValue);
					portfolio.setQuantity(portfolio.getQuantity()-trade.getQuantity());
					portfolioDao.updatePortfolio(portfolio);
					BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
					walletAmount=walletAmount.add(trade.getOrder().getTargetPrice());
					clientService.updateClientWallet(trade.getOrder().getClientId(), walletAmount);
				}
			}
		}catch(Exception e) {
			throw new Exception("Exception while aggregating portfolio");
		}
		
	}
	
}
