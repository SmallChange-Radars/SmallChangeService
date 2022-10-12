package com.fidelity.smallchange.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

public class TradeOrderDaoImpl implements TradeOrderDao {

	DataSource dataSource;
	private final Logger logger = LoggerFactory.getLogger(TradeOrderDaoImpl.class);

	public TradeOrderDaoImpl(DataSource ds) {
		dataSource = ds;
	}

	private final String getTradesByClient = """
						SELECT
			    t.tradeid,
			    t.orderid,
			    t.quantity,
			    t.direction,
			    t.clientid,
			    t.instrumentid,
			    t.executionprice,
			    t.cashvalue,
			    o.targetprice
			FROM
			    trade            t
			    LEFT JOIN orderinstrument  o ON t.orderid = o.orderid;
						""";

	private final String insertTrade = """
			
			""";

	@Override
	public List<Trade> getTradesByClient(Client client) {
		List<Trade> trades = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getTradesByClient)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Order order = new Order(rs.getString("instrumentid"), rs.getBigDecimal("quantity"), rs.getBigDecimal("targetprice"), rs.getString("direction"), rs.getString("clientid"), rs.getString("orderid"));
				Trade trade = new Trade(rs.getString("instrumentid"), rs.getBigDecimal("quantity"), rs.getBigDecimal("executionprice"), rs.getString("direction"), order, rs.getString("tradeid"), rs.getBigDecimal("cashvalue"));
				trades.add(trade);
			}

		} catch (SQLException e) {
			String msg = "Cannot retrieve clients";
			logger.error(msg + ": " + e);

		}

		return trades;
	}

	@Override
	public void insertTrade(Trade trade) {
		
	}

}
