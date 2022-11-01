package com.fidelity.smallchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.fidelity.smallchange.integration.mapper.ClientMapper;
import com.fidelity.smallchange.model.ClientDB;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	ClientMapper userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClientDB user = userRepository.getClientByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}

		return UserDetailsImpl.build(user);
	}

}