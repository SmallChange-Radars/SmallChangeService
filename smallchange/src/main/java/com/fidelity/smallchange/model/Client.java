package com.fidelity.smallchange.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {
	private String clientId;
	private String email;
	private String dateOfBirth;
	private String country;
	private String postalCode;
	private List<ClientIdentification> clientIdentification;
	private String token;
	
	public Client() {};
	
	public Client(String clientId, String email, String dateOfBirth, String country, String postalCode,
			List<ClientIdentification> clientIdentification, String token) {
		super();
		this.clientId = clientId;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.clientIdentification = clientIdentification;
		this.token = token;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public List<ClientIdentification> getClientIdentification() {
		return clientIdentification;
	}
	public void setClientIdentification(List<ClientIdentification> clientIdentification) {
		this.clientIdentification = clientIdentification;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, clientIdentification, country, dateOfBirth, email, postalCode, token);
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
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(token, other.token);
	}
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", country="
				+ country + ", postalCode=" + postalCode + ", clientIdentification=" + clientIdentification + ", token="
				+ token + "]";
	}
	
	
}
