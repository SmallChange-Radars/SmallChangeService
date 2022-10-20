package com.fidelity.smallchange.model;

import java.util.Objects;

public class ClientPreferences {
	
	
	public ClientPreferences(String clientId, String investmentPurpose, String riskTolerance,
			String incomeCategory, String investmentLength) {
		super();
		this.clientId = clientId;
		this.investmentPurpose = investmentPurpose;
		this.riskTolerance = RiskTolerance.of(riskTolerance);
		this.incomeCategory = IncomeCategory.of(incomeCategory);
		this.investmentLength = InvestmentLength.of(investmentLength);
	}
	private String clientId;
	private String investmentPurpose;
	private RiskTolerance riskTolerance;
	private IncomeCategory incomeCategory;
	private InvestmentLength investmentLength;
	
	public String getClientId() {
		return clientId;
	}
	public String getInvestmentPurpose() {
		return investmentPurpose;
	}
	public RiskTolerance getRiskTolerance() {
		return riskTolerance;
	}
	public IncomeCategory getIncomeCategory() {
		return incomeCategory;
	}
	public InvestmentLength getInvestmentLength() {
		return investmentLength;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, incomeCategory, investmentLength, investmentPurpose, riskTolerance);
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
				&& investmentLength == other.investmentLength
				&& Objects.equals(investmentPurpose, other.investmentPurpose) && riskTolerance == other.riskTolerance;
	}
	@Override
	public String toString() {
		return "ClientPreferences [clientId=" + clientId + ", investmentPurpose=" + investmentPurpose
				+ ", riskTolerance=" + riskTolerance + ", incomeCategory=" + incomeCategory + ", investmentLength="
				+ investmentLength + "]";
	}
	
	
}
