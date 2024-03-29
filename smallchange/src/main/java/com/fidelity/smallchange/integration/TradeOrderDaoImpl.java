package com.fidelity.smallchange.integration;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Trade;

@Repository
public class TradeOrderDaoImpl implements TradeOrderDao {

	@Autowired
	private TradeExecutionMapper tradeExecutionMapper;


	@Override
	public List<Trade> getTradeActivityByClient(String clientId,String q,String _category,String _sort,String _order,int offset,int _limit) {
		
		return tradeExecutionMapper.getTradesByClient(clientId,q,_category,_sort,_order,offset,_limit);
	}
	
	@Override
	@Transactional
	public boolean insertOrder(Order order) {
		return tradeExecutionMapper.insertOrder(order)==1;
	}
	
	
	@Override
	@Transactional
	public boolean insertTrade(Trade trade) {
		
		return tradeExecutionMapper.insertTrade(trade) == 1;
	}

	@Override
	public BigDecimal getWalletAmount(String clientId) {
		return tradeExecutionMapper.getWalletAmount(clientId); 
	}

	@Override
	public Portfolio getInstrumentQuantity(String clientId, String instrumentId) {
		return tradeExecutionMapper.getInstrumentQuantity(clientId, instrumentId);
	}

	@Override
	public int totalTradesByClientId(String clientId, String q, String _category) {
		return tradeExecutionMapper.totalTradesByClientId(clientId,q,_category);
	}

}
