package com.fidelity.smallchange.model;

import java.util.Objects;

public class Instrument {
	private String instrumentId;
	private String description;
	private String externalIdType;
	private String externalId;
	private String categoryId;
	private int minQuantity;
	private int maxQuantity;

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, description, externalId, externalIdType, instrumentId, maxQuantity,
				minQuantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrument other = (Instrument) obj;
		return Objects.equals(categoryId, other.categoryId) && Objects.equals(description, other.description)
				&& Objects.equals(externalId, other.externalId) && Objects.equals(externalIdType, other.externalIdType)
				&& Objects.equals(instrumentId, other.instrumentId) && maxQuantity == other.maxQuantity
				&& minQuantity == other.minQuantity;
	}

	public Instrument(String instrumentId, String description, String externalIdType, String externalId,
			String categoryId, int minQuantity, int maxQuantity) {
		super();
		this.instrumentId = instrumentId;
		this.description = description;
		this.externalIdType = externalIdType;
		this.externalId = externalId;
		this.categoryId = categoryId;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
	}

	@Override
	public String toString() {
		return "Instrument [instrumentId=" + instrumentId + ", description=" + description + ", externalIdType="
				+ externalIdType + ", externalId=" + externalId + ", categoryId=" + categoryId + ", minQuantity="
				+ minQuantity + ", maxQuantity=" + maxQuantity + "]";
	}
}
