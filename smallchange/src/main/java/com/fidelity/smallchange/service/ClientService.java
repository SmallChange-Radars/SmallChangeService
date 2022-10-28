package com.fidelity.smallchange.service;

import java.text.ParseException;

import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.Token;

public interface ClientService {
	Client clientVerification(ClientDB client);

	Token getToken(ClientDB client) throws ParseException;

	void insertToken(Client verified);
}
