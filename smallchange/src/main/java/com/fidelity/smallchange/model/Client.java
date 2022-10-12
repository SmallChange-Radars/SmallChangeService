package com.fidelity.smallchange.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Client {
	private String clientId;
	private String email;
	private String dateOfBirth;
	private Country country;
	private String postalCode;
	private List<ClientIdentification> clientIdentification;
	
	public Client(String clientId, String email, String dateOfBirth, Country country, String postalCode, ClientIdentification clientIdentification) {
		this.clientId = clientId;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.clientIdentification = new ArrayList<>();
		this.clientIdentification.add(clientIdentification);
	}
	
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
	public List<ClientIdentification> getClientIdentification() {
		return clientIdentification;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, clientIdentification, country, dateOfBirth, email, postalCode);
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
				&& Objects.equals(clientIdentification, other.clientIdentification) && country == other.country
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(email, other.email)
				&& Objects.equals(postalCode, other.postalCode);
	}
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", country="
				+ country + ", postalCode=" + postalCode + ", clientIdentification=" + clientIdentification + "]";
	}
	
}
