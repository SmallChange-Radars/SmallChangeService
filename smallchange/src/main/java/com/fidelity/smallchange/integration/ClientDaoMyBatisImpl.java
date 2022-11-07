package com.fidelity.smallchange.integration;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.smallchange.integration.mapper.ClientMapper;
import com.fidelity.smallchange.model.ClientDB;

@Repository
public class ClientDaoMyBatisImpl implements ClientDao {

	@Autowired
	ClientMapper mapper;
	
	@Override
	public ClientDB getClientById(String clientId) {
		ClientDB client = mapper.getClientByClientId(clientId);
		return client;
	}

	@Override
	public int insertClient(ClientDB client) {
		int count = mapper.insertClient(client);
		return count;
	}

	@Override
	public int checkClientByEmail(String email) {
		int count = mapper.getNumberOfClientsByEmail(email);
		return count;
	}

	@Override
	public ClientDB getClientWalletById(String clientId) {
		ClientDB client = mapper.getClientWalletByClientId(clientId);
		return client;
	}

	@Override
	public int updateClientWallet(BigDecimal wallet, String clientId) {
		int count = mapper.updateClientWallet(wallet, clientId);
		return count;
	}

}
