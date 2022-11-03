package com.fidelity.smallchange.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.smallchange.integration.mapper.ClientIdentificationMapper;
import com.fidelity.smallchange.model.ClientIdentification;

@Repository
public class ClientIdentificationDaoMyBatisImpl implements ClientIdentificationDao{

	@Autowired
	ClientIdentificationMapper mapper;
	
	@Override
	public List<ClientIdentification> getClientIdentification(String clientId) {
		List<ClientIdentification> identification = mapper.getClientIdentificationByClientId(clientId);
		return identification;
	}

	@Override
	public int insertClientIdentification(String type, String value, String clientId) {
		int count = mapper.insertClientIdentification(type, value, clientId);
		return count;
	}

	@Override
	public int updateClientIdentification(String type, String value, String clientId) {
		int count = mapper.updateClientIdentification(type, value, clientId);
		return count;
	}

}
