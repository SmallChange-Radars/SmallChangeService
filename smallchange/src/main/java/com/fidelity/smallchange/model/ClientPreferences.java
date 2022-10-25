package com.fidelity.smallchange.model;

import java.util.Objects;

public class ClientPreferences {
	
	
	public ClientPreferences(String clientId, String investmentPurpose, int i,
			int j, int k) {
		super();
		this.clientId = clientId;
		this.investmentPurpose = investmentPurpose;
		this.riskTolerance = i;
		this.incomeCategory = j;
		this.lengthOfInvestment = k;
	}
	private String clientId;
	private String investmentPurpose;
	private int riskTolerance;
	private int incomeCategory;
	private int lengthOfInvestment;
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getInvestmentPurpose() {
		return investmentPurpose;
	}
	public void setInvestmentPurpose(String investmentPurpose) {
		this.investmentPurpose = investmentPurpose;
	}
	public int getRiskTolerance() {
		return riskTolerance;
	}
	public void setRiskTolerance(int riskTolerance) {
		this.riskTolerance = riskTolerance;
	}
	public int getIncomeCategory() {
		return incomeCategory;
	}
	public void setIncomeCategory(int incomeCategory) {
		this.incomeCategory = incomeCategory;
	}
	public int getLengthOfInvestment() {
		return lengthOfInvestment;
	}
	public void setLengthOfInvestment(int lengthOfInvestment) {
		this.lengthOfInvestment = lengthOfInvestment;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, incomeCategory, lengthOfInvestment, investmentPurpose, riskTolerance);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientPreferences other = (ClientPreferences) obj;
		return Objects.equals(clientId, other.clientId) && incomeCategory == other.incomeCategory
				&& lengthOfInvestment == other.lengthOfInvestment
				&& Objects.equals(investmentPurpose, other.investmentPurpose) && riskTolerance == other.riskTolerance;
	}
	@Override
	public String toString() {
		return "ClientPreferences [clientId=" + clientId + ", investmentPurpose=" + investmentPurpose
				+ ", riskTolerance=" + riskTolerance + ", incomeCategory=" + incomeCategory + ", investmentLength="
				+ lengthOfInvestment + "]";
	}
	
	
}
