package com.fidelity.smallchange.model;

import java.util.Objects;

public class Token {
	private String clientId,token, timestamp;

	public Token(String clientId, String token, String timestamp) {
		super();
		this.clientId = clientId;
		this.token = token;
		this.timestamp = timestamp;
	}

	public Token() {
		// TODO Auto-generated constructor stub
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientId, timestamp, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(timestamp, other.timestamp)
				&& Objects.equals(token, other.token);
	}

	@Override
	public String toString() {
		return "Token [clientId=" + clientId + ", token=" + token + "]";
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
