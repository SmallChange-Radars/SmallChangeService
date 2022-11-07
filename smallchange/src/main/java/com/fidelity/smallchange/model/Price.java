package com.fidelity.smallchange.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
	private BigDecimal bidPrice;
	private BigDecimal askPrice;
	private String timestamp;
	private Instrument instrument;
	
	public BigDecimal getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}
	public BigDecimal getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(BigDecimal askPrice) {
		this.askPrice = askPrice;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	@Override
	public int hashCode() {
		return Objects.hash(askPrice, bidPrice, instrument, timestamp);
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
		return Objects.equals(askPrice, other.askPrice) && Objects.equals(bidPrice, other.bidPrice)
				&& Objects.equals(instrument, other.instrument) && Objects.equals(timestamp, other.timestamp);
	}
	@Override
	public String toString() {
		return "Price [bidPrice=" + bidPrice + ", askPrice=" + askPrice + ", timestamp=" + timestamp + ", instrument="
				+ instrument + "]";
	}

}
