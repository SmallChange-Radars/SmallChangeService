package com.fidelity.smallchange.model;

import java.util.Arrays;
import java.util.Objects;

public class Client {
	private String clientId;
	private String email;
	private String dateOfBirth;
	private Country country;
	private String postalCode;
	private ClientIdentification[] clientIdentification;
	
	public String getClientId() {
		return clientId;
	}
	public String getEmail() {
		return email;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public Country getCountry() {
		return country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public ClientIdentification[] getClientIdentification() {
		return clientIdentification;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(clientIdentification);
		result = prime * result + Objects.hash(clientId, country, dateOfBirth, email, postalCode);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(clientId, other.clientId)
				&& Arrays.equals(clientIdentification, other.clientIdentification) && country == other.country
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(email, other.email)
				&& Objects.equals(postalCode, other.postalCode);
	}
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", country="
				+ country + ", postalCode=" + postalCode + ", clientIdentification="
				+ Arrays.toString(clientIdentification) + "]";
	}
	
}
