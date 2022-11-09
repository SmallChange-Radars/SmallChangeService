package com.fidelity.smallchange.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.JwtResponse;
import com.fidelity.smallchange.model.MessageResponse;
import com.fidelity.smallchange.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	ClientService cs;

	
	private static final String DB_ERROR_MSG = "Error communicating with the Smallchange database";

	@PostMapping(value="/signin", produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody ClientDB loginRequest) {

		JwtResponse response;
		try {
			response = cs.loginClient(loginRequest);
			return ResponseEntity.ok(response);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping(value="/signup", produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUser(@Validated @RequestBody ClientDB signUpRequest) {
		if (cs.checkClientByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		Client client = new Client();
		try {
			client = cs.clientVerification(signUpRequest);
		} catch (HttpClientErrorException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials", e);
		}

		try {
			cs.insertClient(signUpRequest, client);
		}
		catch(RuntimeException e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}