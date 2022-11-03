package com.fidelity.smallchange.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.ClientDao;
import com.fidelity.smallchange.integration.ClientIdentificationDao;
import com.fidelity.smallchange.integration.DatabaseException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.mapper.TokenMapper;
import com.fidelity.smallchange.jwt.JwtUtils;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.JwtResponse;
import com.fidelity.smallchange.model.Token;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	FMTSRestClient rc;

	@Autowired
	TokenMapper tm;
	
	@Autowired
	ClientDao dao;
	
	@Autowired
	ClientIdentificationDao identificationDao;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	@Override
	public Client clientVerification(ClientDB client) throws HttpClientErrorException {
		Client verified = new Client();
		try {
			verified = rc.clientVerification(new Client("", client.getEmail(), client.getDateOfBirth(),
					client.getCountry().getCode(), client.getPostalCode(), client.getClientIdentification(), ""));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verified;
	}

	
	private void insertToken(Client verified) {
		tm.insertToken(new Token(verified.getClientId(), verified.getToken(),
				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
	}

	private Token tokenGeneration(ClientDB client) throws HttpClientErrorException {
		Token token = new Token();
		try {
			Client verified = rc.clientVerification(new Client(client.getClientId(), client.getEmail(),
					client.getDateOfBirth(), client.getCountry().getCode(), client.getPostalCode(),
					client.getClientIdentification(), ""));
			token = new Token(verified.getClientId(), verified.getToken(),
					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
			tm.updateToken(token);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}

	@Override
	public Token getToken(String clientId) throws ParseException, HttpClientErrorException {
		Token token = tm.getTokenByClientId(clientId);
		System.out.println("here"+token.getToken());
		Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(token.getTimestamp());
		if ((new Date().getTime() - date1.getTime()) <= (5 * 60 * 1000)) {
			tm.updateToken(token);
		} else {
			token = tokenGeneration(dao.getClientById(clientId));
		}
		return token;
	}

	@Override
	public ClientDB getClientByClientId(String clientId) {
		ClientDB client = null;
		try {
			client = dao.getClientById(clientId);
		}
		catch(Exception e) {
			String msg = String.format("Error querying For Client with id = %s in the Smallchange database.", clientId);
			throw new DatabaseException(msg, e);
		}
		return client;
	}

	@Override
	public int insertClient(ClientDB signUpRequest, Client client) {
		int count = 0;
		signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
		signUpRequest.setClientId(client.getClientId());
		List<ClientIdentification> identification = signUpRequest.getClientIdentification();
		try {
			count = dao.insertClient(signUpRequest);
			if(identification!=null)
			for(ClientIdentification id: identification) {
				identificationDao.insertClientIdentification(id.getType(), encoder.encode(id.getValue()), client.getClientId());
			}
		}
		catch(Exception e) {
			String msg = "Error inserting Client in the Smallchange database.";
			throw new DatabaseException(msg, e);
		}
		insertToken(client);
		return count;
	}

	@Override
	public boolean checkClientByEmail(String email) {
		boolean result = true;
		int count = 0;
		
		try {
			count = dao.checkClientByEmail(email);
			result = count>0? true : false;
		}
		catch(Exception e) {
			String msg = "Error checking for client in the Smallchange database.";
			throw new DatabaseException(msg, e);
		}
		return result;
	}

	@Override
	public JwtResponse loginClient(ClientDB loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtResponse(accessToken, userDetails.getUsername(), roles);
	}

}
