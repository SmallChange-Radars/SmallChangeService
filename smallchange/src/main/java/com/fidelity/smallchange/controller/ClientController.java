package com.fidelity.smallchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.service.ClientService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	@GetMapping("/clientInfo")
	public ClientDB getClient(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication
                .getPrincipal();
		System.out.println(userDetails.getClientId());
//		return service.getClient(userDetails.getClientId());
		return null;
	}
	
	@PostMapping("/signup")
	public void postClient(@RequestBody ClientDB body) {
		// service.postClient(body);
	}
}
