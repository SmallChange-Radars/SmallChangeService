package com.fidelity.smallchange.model;

import java.util.Objects;

public class Price {
	private String instrumentId;
	private double bidPrice;
	private double askPrice;
	private String timestamp;
	private Instrument instrument;
	@Override
	public int hashCode() {
		return Objects.hash(askPrice, bidPrice, instrument, instrumentId, timestamp);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		return Double.doubleToLongBits(askPrice) == Double.doubleToLongBits(other.askPrice)
				&& Double.doubleToLongBits(bidPrice) == Double.doubleToLongBits(other.bidPrice)
				&& Objects.equals(instrument, other.instrument) && Objects.equals(instrumentId, other.instrumentId)
				&& Objects.equals(timestamp, other.timestamp);
	}
	@Override
	public String toString() {
		return "Price [instrumentId=" + instrumentId + ", bidPrice=" + bidPrice + ", askPrice=" + askPrice
				+ ", timestamp=" + timestamp + ", instrument=" + instrument + "]";
	}
	public Price(String instrumentId, double bidPrice, double askPrice, String timestamp, Instrument instrument) {
		super();
		this.instrumentId = instrumentId;
		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
		this.timestamp = timestamp;
		this.instrument = instrument;
	}
}
