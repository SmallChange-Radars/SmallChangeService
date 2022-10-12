package com.fidelity.smallchange.model;

import java.util.Objects;

public class ClientPreferences {
	private String investmentPurpose;
	private String riskTolerance;
	private String incomeCategory;
	private String investmentLength;
	
	public String getInvestmentPurpose() {
		return investmentPurpose;
	}
	public String getRiskTolerance() {
		return riskTolerance;
	}
	public String getIncomeCategory() {
		return incomeCategory;
	}
	public String getInvestmentLength() {
		return investmentLength;
	}
	@Override
	public int hashCode() {
		return Objects.hash(incomeCategory, investmentLength, investmentPurpose, riskTolerance);
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
		return Objects.equals(incomeCategory, other.incomeCategory)
				&& Objects.equals(investmentLength, other.investmentLength)
				&& Objects.equals(investmentPurpose, other.investmentPurpose)
				&& Objects.equals(riskTolerance, other.riskTolerance);
	}
	@Override
	public String toString() {
		return "ClientPreferences [investmentPurpose=" + investmentPurpose + ", riskTolerance=" + riskTolerance
				+ ", incomeCategory=" + incomeCategory + ", investmentLength=" + investmentLength + "]";
	}
}
