package com.fidelity.smallchange.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
	private String instrumentId;
	private int quantity;
	private BigDecimal targetPrice;
	private String direction;
	private String clientId;
	private int orderId;
	private String token;
	
	public Order() {
		
	}
	
	public Order(String instrumentId, int quantity, BigDecimal targetPrice, String direction, String clientId,
			int orderId) {
		super();
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.targetPrice =targetPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.orderId = orderId;
		this.token = token;
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

	public BigDecimal getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientId, direction, instrumentId, orderId, quantity, targetPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(direction, other.direction)
				&& Objects.equals(instrumentId, other.instrumentId) && Objects.equals(orderId, other.orderId)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(targetPrice, other.targetPrice);
	}

	@Override
	public String toString() {
		return "Order [instrumentId=" + instrumentId + ", quantity=" + quantity + ", targetPrice=" + targetPrice
				+ ", direction=" + direction + ", clientId=" + clientId + ", orderId=" + orderId + ", token=" + token
				+ "]";
	}

	
	
	
	
}
