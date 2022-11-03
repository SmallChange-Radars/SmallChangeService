package com.fidelity.smallchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.service.ClientService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/client")
public class ClientController {
	private static final String DB_ERROR_MSG = "Error communicating with the Smallchange database";
	
	@Autowired
	private ClientService service;
	
	@GetMapping("/clientInfo")
	public ResponseEntity<ClientDB> getClient(Authentication authentication) {
		ClientDB client;
		ResponseEntity<ClientDB> result;
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication
                .getPrincipal();
		String clientId = userDetails.getClientId();
		try {
			client = service.getClientByClientId(clientId);
		}
		catch(RuntimeException e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if(client == null) {
			result = ResponseEntity.noContent().build();
		}
		else
			result = ResponseEntity.ok(client);
		return result;
	}
	
}
