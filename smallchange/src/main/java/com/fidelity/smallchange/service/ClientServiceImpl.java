package com.fidelity.smallchange.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.ClientDao;
import com.fidelity.smallchange.integration.DatabaseException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.mapper.TokenMapper;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.Token;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	FMTSRestClient rc;

	@Autowired
	TokenMapper tm;
	
	@Autowired
	ClientDao dao;

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

	@Override
	public void insertToken(Client verified) {
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
	public Token getToken(ClientDB client) throws ParseException, HttpClientErrorException {
		Token token = tm.getTokenByClientId(client.getClientId());
		Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(token.getTimestamp());
		if ((new Date().getTime() - date1.getTime()) <= (5 * 60 * 1000)) {
			tm.updateToken(token);
		} else {
			token = tokenGeneration(client);
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
	public int insertClient(ClientDB client) {
		int count = 0;
		
		try {
			count = dao.insertClient(client);
		}
		catch(Exception e) {
			String msg = "Error inserting Client in the Smallchange database.";
			throw new DatabaseException(msg, e);
		}
		return count;
	}

}
