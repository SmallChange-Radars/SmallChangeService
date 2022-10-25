package com.fidelity.smallchange.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ClientDB {
	private String clientId;
	private String email;
	private String dateOfBirth;
	private Country country;
	private String postalCode;
	private List<ClientIdentification> clientIdentification;
	private String token;
	private String password;
	private BigDecimal wallet;
	private String walletCurrency;
	
	public ClientDB() {
		
	}
	
	public ClientDB(String clientId, String email, String dateOfBirth, Country country, String postalCode,
			List<ClientIdentification> ciList, BigDecimal wallet, String walletCurrency) {
		this.clientId = clientId;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.clientIdentification = ciList;
		this.wallet = wallet;
		this.walletCurrency = walletCurrency;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientId, clientIdentification, country, dateOfBirth, email, password, postalCode, token,
				wallet, walletCurrency);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientDB other = (ClientDB) obj;
		return Objects.equals(clientId, other.clientId)
				&& Objects.equals(clientIdentification, other.clientIdentification) && country == other.country
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(email, other.email)
				&& Objects.equals(password, other.password) && Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(token, other.token) && Objects.equals(wallet, other.wallet)
				&& Objects.equals(walletCurrency, other.walletCurrency);
	}
	@Override
	public String toString() {
		return "ClientDB [clientId=" + clientId + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", country="
				+ country + ", postalCode=" + postalCode + ", clientIdentification=" + clientIdentification + ", token="
				+ token + ", password=" + password + ", wallet=" + wallet + ", walletCurrency=" + walletCurrency + "]";
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
	public String getToken() {
		return token;
	}
	public String getPassword() {
		return password;
	}
	public BigDecimal getWallet() {
		return wallet;
	}
	public String getWalletCurrency() {
		return walletCurrency;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setClientIdentification(List<ClientIdentification> clientIdentification) {
		this.clientIdentification = clientIdentification;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setWallet(BigDecimal wallet) {
		this.wallet = wallet;
	}

	public void setWalletCurrency(String walletCurrency) {
		this.walletCurrency = walletCurrency;
	}
}
