package com.fidelity.smallchange.model;

import java.math.BigDecimal;
import java.util.Objects;


public class Trade {
	private String instrumentId;
	private int quantity;
	private BigDecimal executionPrice;
	private String direction;
	private Order order;
	private String tradeId;
	private BigDecimal cashValue;
	
	
	public Trade(String instrumentId, int quantity, BigDecimal executionPrice, String direction, Order order,
			String tradeId, BigDecimal cashValue) {
		super();
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.executionPrice = executionPrice;
		this.direction = direction;
		this.order = order;
		this.tradeId = tradeId;
		this.cashValue = cashValue;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getExecutionPrice() {
		return executionPrice;
	}
	public void setExecutionPrice(BigDecimal executionPrice) {
		this.executionPrice = executionPrice;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public BigDecimal getCashValue() {
		return cashValue;
	}
	public void setCashValue(BigDecimal cashValue) {
		this.cashValue = cashValue;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cashValue, direction, executionPrice, instrumentId, order, quantity, tradeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		return Objects.equals(cashValue, other.cashValue) && Objects.equals(direction, other.direction)
				&& Objects.equals(executionPrice, other.executionPrice)
				&& Objects.equals(instrumentId, other.instrumentId) && Objects.equals(order, other.order)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(tradeId, other.tradeId);
	}
	@Override
	public String toString() {
		return "Trade [instrumentId=" + instrumentId + ", quantity=" + quantity + ", executionPrice=" + executionPrice
				+ ", direction=" + direction + ", order=" + order + ", tradeId=" + tradeId + ", cashValue=" + cashValue
				+ "]";
	}
	
	

}
