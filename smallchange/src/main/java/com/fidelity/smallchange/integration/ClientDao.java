package com.fidelity.smallchange.integration;

import java.math.BigDecimal;

import com.fidelity.smallchange.model.ClientDB;

public interface ClientDao {
	
	ClientDB getClientById(String clientId);
	int insertClient(ClientDB client);
	int checkClientByEmail(String email);
	ClientDB getClientWalletById(String clientId);
	int updateClientWallet(BigDecimal wallet, String clientId);
}
