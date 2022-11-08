package com.fidelity.smallchange.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InstrumentPrice {
	private String instrumentId;
	private String instrumentDescription;
	private String externalIdType;
	private String externalId;
	private String categoryId;
	private int minQuantity;
	private int maxQuantity;
	private BigDecimal bidPrice;
	private BigDecimal askPrice;
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getInstrumentDescription() {
		return instrumentDescription;
	}
	public void setInstrumentDescription(String instrumentDescription) {
		this.instrumentDescription = instrumentDescription;
	}
	public String getExternalIdType() {
		return externalIdType;
	}
	public void setExternalIdType(String externalIdType) {
		this.externalIdType = externalIdType;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public int getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}
	public int getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
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
	public InstrumentPrice(String instrumentId, String instrumentDescription, String externalIdType, String externalId,
			String categoryId, int minQuantity, int maxQuantity, BigDecimal bidPrice, BigDecimal askPrice) {
		super();
		this.instrumentId = instrumentId;
		this.instrumentDescription = instrumentDescription;
		this.externalIdType = externalIdType;
		this.externalId = externalId;
		this.categoryId = categoryId;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
	}
	public InstrumentPrice() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(askPrice, bidPrice, categoryId, externalId, externalIdType, instrumentDescription,
				instrumentId, maxQuantity, minQuantity);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentPrice other = (InstrumentPrice) obj;
		return Objects.equals(askPrice, other.askPrice) && Objects.equals(bidPrice, other.bidPrice)
				&& Objects.equals(categoryId, other.categoryId) && Objects.equals(externalId, other.externalId)
				&& Objects.equals(externalIdType, other.externalIdType)
				&& Objects.equals(instrumentDescription, other.instrumentDescription)
				&& Objects.equals(instrumentId, other.instrumentId) && maxQuantity == other.maxQuantity
				&& minQuantity == other.minQuantity;
	}
	@Override
	public String toString() {
		return "InstrumentPrice [instrumentId=" + instrumentId + ", instrumentDescription=" + instrumentDescription
				+ ", externalIdType=" + externalIdType + ", externalId=" + externalId + ", categoryId=" + categoryId
				+ ", minQuantity=" + minQuantity + ", maxQuantity=" + maxQuantity + ", bidPrice=" + bidPrice
				+ ", askPrice=" + askPrice + "]";
	}
}
