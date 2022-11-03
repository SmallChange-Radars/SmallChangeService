package com.fidelity.smallchange.integration;

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

}
