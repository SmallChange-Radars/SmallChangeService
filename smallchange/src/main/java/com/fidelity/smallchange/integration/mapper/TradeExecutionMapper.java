package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;



@Mapper
public interface TradeExecutionMapper {

	@Select("SELECT\r\n" + "			    t.tradeid,\r\n" + "			    t.orderid,\r\n"
			+ "			    t.quantity,\r\n" + "			    t.direction,\r\n" + "			    t.clientid,\r\n"
			+ "			    t.instrumentid,\r\n" + "			    t.executionprice,\r\n"
			+ "			    t.cashvalue,\r\n" + "			    o.targetprice\r\n" + "			FROM\r\n"
			+ "			    trade            t\r\n"
			+ "			    LEFT JOIN orderinstrument  o ON t.orderid = o.orderid where t.clientID = #{clientId}")
	public List<Trade> getTradesByClient(Client client);

	@Insert("INSERT INTO orderinstrument ( orderid,quantity, targetprice, direction, clientid, instrumentid)"
			+ " values (#{orderid},#{quantity},#{targetprice},#{direction},#{clientid},#{instrumentid})")
	public int insertOrder(Order order);
	
	@Insert("INSERT INTO trade values (#{tradeId},#{order.orderID},#{quantity},#{direction},#{clientID},#{instrumentID}"
			+ "#{exceutionPrice},#{cashValue})")
	public int insertTrade(Trade trade);

}
