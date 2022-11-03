package com.fidelity.smallchange.service;

import java.text.ParseException;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.JwtResponse;
import com.fidelity.smallchange.model.Token;

public interface ClientService {
	Client clientVerification(ClientDB client);

	Token getToken(String clientId) throws ParseException;
	
	ClientDB getClientByClientId(String clientId);
	
	int insertClient(ClientDB signUpRequest, Client client);
	
	boolean checkClientByEmail (String email);

	JwtResponse loginClient(ClientDB loginRequest);
}
