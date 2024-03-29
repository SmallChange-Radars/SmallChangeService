package com.fidelity.smallchange.integration;

import java.math.BigDecimal;
import java.util.List;

import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Trade;



public interface TradeOrderDao {
	
	
	
	List<Trade> getTradeActivityByClient(String clientId,String q,String _category,String _sort,String _order,int offset,int _limit);
	
	int totalTradesByClientId(String clientId, String q, String _category);
	
	boolean insertOrder(Order order);
	
	boolean insertTrade(Trade trade);
	
	BigDecimal getWalletAmount(String clientId);
	
	Portfolio getInstrumentQuantity(String clientId, String instrumentId);

}
