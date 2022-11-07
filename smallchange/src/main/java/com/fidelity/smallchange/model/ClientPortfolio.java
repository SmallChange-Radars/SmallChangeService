package com.fidelity.smallchange.model;

import java.math.BigDecimal;

public class ClientPortfolio {
	private String clientId;
    private String instrumentId;
    private int quantity;
    private BigDecimal value;
    private BigDecimal currentPrice;
    private BigDecimal gains;
    private double returns;
    
	public ClientPortfolio(String clientId, String instrumentId, int quantity, BigDecimal value,
			BigDecimal currentPrice, BigDecimal gains, double returns) {
		super();
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.value = value;
		this.currentPrice = currentPrice;
		this.gains = gains;
		this.returns = returns;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getGains() {
		return gains;
	}

	public void setGains(BigDecimal gains) {
		this.gains = gains;
	}

	public double getReturns() {
		return returns;
	}

	public void setReturns(double returns) {
		this.returns = returns;
	}
}
