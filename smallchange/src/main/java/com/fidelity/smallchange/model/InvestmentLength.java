package com.fidelity.smallchange.model;

public enum InvestmentLength {
	LENGTHONE(1, "0-5"), LENGTHTWO(2, "5-7"), LENGTHTHREE(3, "7-10"), LENGTHFOUR(4, "10-15");
	
	private String name;
	private int code;
	
	private InvestmentLength(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static InvestmentLength of(int code) {
		for(InvestmentLength investment: values()) {
			if(investment.code == code)
				return investment;
		}
		throw new IllegalArgumentException("Invalid Risk Tolerance Code");
	}
	
	public static InvestmentLength of(String name) {
		for(InvestmentLength investment: values()) {
			if(investment.name == name)
				return investment;
		}
		throw new IllegalArgumentException("Invalid Risk Tolerance Name");
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
};
