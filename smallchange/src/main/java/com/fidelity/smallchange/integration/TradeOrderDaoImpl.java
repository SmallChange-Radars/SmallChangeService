package com.fidelity.smallchange.integration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.integration.mapper.TradeExecutionMapper;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

@Repository
public class TradeOrderDaoImpl implements TradeOrderDao {

	@Autowired
	private TradeExecutionMapper tradeExecutionMapper;
	
	private final Logger logger = LoggerFactory.getLogger(TradeOrderDaoImpl.class);


	@Override
	public List<Trade> getTradeActivityByClient(String clientId) {
		return tradeExecutionMapper.getTradesByClient(clientId);
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
	public int getInstrumentQuantity(String clientId, String instrumentId) {
		return tradeExecutionMapper.getInstrumentQuantity(clientId, instrumentId);
	}

}
