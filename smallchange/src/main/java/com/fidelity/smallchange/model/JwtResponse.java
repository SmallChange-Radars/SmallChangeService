package com.fidelity.smallchange.model;

import java.util.List;
import java.util.Objects;

public class JwtResponse {

	private String JWT, username;
	private List<String> roles;
	
	public JwtResponse() {}
	
	public JwtResponse(String jWT, String username, List<String> roles) {
		super();
		JWT = jWT;
		this.username = username;
		this.roles = roles;
	}
	@Override
	public int hashCode() {
		return Objects.hash(JWT, roles, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtResponse other = (JwtResponse) obj;
		return Objects.equals(JWT, other.JWT) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	public String getJWT() {
		return JWT;
	}
	public void setJWT(String jWT) {
		JWT = jWT;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "JwtResponse [JWT=" + JWT + ", username=" + username + ", roles=" + roles + "]";
	}

}
