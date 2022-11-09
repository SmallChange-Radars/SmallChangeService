package com.fidelity.smallchange.service;

import java.util.List;

import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

public interface TradeService {
	public List<Trade> getTradeActivityByClientId(String clientId,String q,String _category,int _page,int _limit,String _sort,String _order) throws Exception;
	
	public int totalTradesByClientId(String clientId, String q, String _category) throws Exception;
	
	public boolean tradeExecution(Order order, String clientId) throws Exception;
}
