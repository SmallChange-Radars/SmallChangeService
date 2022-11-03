package com.fidelity.smallchange.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import com.fidelity.smallchange.integration.mapper.ClientMapper;
import com.fidelity.smallchange.jwt.JwtUtils;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.JwtResponse;
import com.fidelity.smallchange.model.MessageResponse;
import com.fidelity.smallchange.service.ClientService;
import com.fidelity.smallchange.service.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	ClientMapper userRepository;

	@Autowired
	ClientService cs;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping(value="/signin", produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody ClientDB loginRequest) {

		try {
			//all except response message
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String accessToken = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(accessToken, userDetails.getUsername(), roles));

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping(value="/signup", produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUser(@Validated @RequestBody ClientDB signUpRequest) {
		if (cs.checkClientByEmail(signUpRequest.getEmail()) == true) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		//first check db and then make request
		Client client = new Client();
		try {
			client = cs.clientVerification(signUpRequest);
		} catch (HttpClientErrorException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials", e);
		}

		//TODO: encrypt whats to be encrypted
		// Create new user's account 
		
		signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
		signUpRequest.setClientId(client.getClientId());

		userRepository.insertClient(signUpRequest);
		
		// Insert into client identification
		
		cs.insertToken(client);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}