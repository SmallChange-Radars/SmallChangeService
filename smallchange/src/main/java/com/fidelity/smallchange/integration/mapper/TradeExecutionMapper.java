package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;

@Mapper
public interface TradeExecutionMapper {

	@Select("SELECT\r\n"
			+ "    t.tradeid,\r\n"
			+ "    t.instrumentid,\r\n"
			+ "    t.quantity,\r\n"
			+ "    t.direction,\r\n"
			+ "    t.executionprice,\r\n"
			+ "    t.cashvalue,\r\n"
			+ "    o.targetprice,\r\n"
			+ "    o.clientid,\r\n"
			+ "    o.orderid\r\n"
			+ "FROM\r\n"
			+ "    trade            t\r\n"
			+ "    LEFT JOIN orderinstrument  o ON t.clientid = o.clientid WHERE\r\n"
			+ "    t.clientid = #{clientId}")
	@ResultMap("com.fidelity.smallchange.integration.TradeExecutionMapper.GetTradesMap")
	public List<Trade> getTradesByClient(String clientId);

	@Insert("INSERT INTO orderinstrument ( orderid,quantity, targetprice, direction, clientid, instrumentid)"
			+ " values (#{orderId},#{quantity},#{targetPrice},#{direction},#{clientId},#{instrumentId})")
	public int insertOrder(Order order);
	
	@Insert("INSERT INTO trade (tradeid, orderid,quantity, direction, clientid, instrumentid,executionprice,cashvalue)"
			+ " values (#{tradeId},#{order.orderId},#{quantity},#{direction},#{order.clientId},#{instrumentId},"
			+ "#{executionPrice},#{cashValue})")
	public int insertTrade(Trade trade);

}