package com.fidelity.smallchange.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Portfolio {
	private String clientId;
    private String instrumentId;
    private int quantity;
    private BigDecimal value;
    
	public Portfolio() {
		
	}
    
    public Portfolio(String clientId, String instrumentId, int quantity, BigDecimal value) {
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.value = value;
	}

	public String getClientId() {
		return clientId;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Portfolio [clientId=" + clientId + ", instrumentId=" + instrumentId + ", quantity=" + quantity
				+ ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientId, instrumentId, quantity, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portfolio other = (Portfolio) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(instrumentId, other.instrumentId)
				&& quantity == other.quantity && Objects.equals(value, other.value);
	}

}
