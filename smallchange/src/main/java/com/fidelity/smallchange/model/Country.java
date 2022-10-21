package com.fidelity.smallchange.model;

public enum Country {
	US("US", "United States of America"), IN("IN", "India"), IE("IE", "Ireland"), OC("OC", "Other Countries");
	
	private String code;
	private String name;
	
	Country(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static Country of(String code) {
		for(Country country: values()) {
			if(country.code == code)
				return country;
		}
		throw new IllegalArgumentException("Invalid Risk Tolerance Code");
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
