package com.fidelity.smallchange.model;

public enum RiskTolerance {
	CONSERVATIVE(1, "Conservative"), BELOWAVERAGE(2, "Below Average"), AVERAGE(3, "Average"), ABOVEAVERAGE(4, "Above Average"), AGGRESIVE(5, "Aggresive");
	
	private String name;
	private int code;
	
	private RiskTolerance(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static RiskTolerance of(int code) {
		for(RiskTolerance tolerance: values()) {
			if(tolerance.code == code)
				return tolerance;
		}
		throw new IllegalArgumentException("Invalid Risk Tolerance Code");
	}
	
	public static RiskTolerance of(String name) {
		for(RiskTolerance tolerance: values()) {
			if(tolerance.name == name)
				return tolerance;
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
