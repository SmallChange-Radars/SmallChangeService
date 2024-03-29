package com.fidelity.smallchange.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fidelity.smallchange.model.ClientDB;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String email;

	private String clientId;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String email, String password, String clientId,
			Collection<? extends GrantedAuthority> authorities) {
		this.email = email;
		this.password = password;

		this.clientId = clientId;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(ClientDB user) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		return new UserDetailsImpl(user.getEmail(), user.getPassword(), user.getClientId(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(email, user.getUsername());
	}

	public String getClientId() {
		return clientId;
	}
}