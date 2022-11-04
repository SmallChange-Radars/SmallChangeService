package com.fidelity.smallchange.integration;

import java.util.List;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;



public interface TradeOrderDao {
	
	
	
	List<Trade> getTradeActivityByClient(String clientId);
	
	boolean insertOrder(Order order);
	
	boolean insertTrade(Trade trade);

}
