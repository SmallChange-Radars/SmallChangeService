package com.fidelity.smallchange.model;

public enum IncomeCategory {
	LEVELONE(1, "0-20,000"), LEVELTWO(2, "20,001-40,000"), LEVELTHREE(3, "40,001-60,000"), LEVELFOUR(4, "60-001-80,000"), LEVELFIVE(5, "80,001-100,000"), LEVELSIX(6, "101,001-150,000"), LEVELSEVEN(7, "150,000+");
	
	private String name;
	private int code;
	
	private IncomeCategory(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static IncomeCategory of(int code) {
		for(IncomeCategory income: values()) {
			if(income.code == code)
				return income;
		}
		throw new IllegalArgumentException("Invalid Risk Tolerance Code");
	}
	
	public static IncomeCategory of(String name) {
		for(IncomeCategory income: values()) {
			if(income.name == name)
				return income;
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