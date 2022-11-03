package com.fidelity.smallchange.integration;

import com.fidelity.smallchange.model.ClientDB;

public interface ClientDao {
	
	ClientDB getClientById(String clientId);
	int insertClient(ClientDB client);
	int checkClientByEmail(String email);
}
