package com.fidelity.smallchange.service;

import java.util.List;

import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

public interface TradeService {
	public List<Trade> getTradeActivityByClientId(String clientId) throws Exception;
	
	public void tradeExecution(Order order) throws Exception;
}
