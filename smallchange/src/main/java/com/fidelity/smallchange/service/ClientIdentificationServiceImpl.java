package com.fidelity.smallchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.ClientIdentificationDao;
import com.fidelity.smallchange.integration.DatabaseException;
import com.fidelity.smallchange.model.ClientIdentification;

@Service
public class ClientIdentificationServiceImpl implements ClientIdentificationService{
	
	@Autowired
	ClientIdentificationDao dao;

	@Override
	public List<ClientIdentification> getClientIdentification(String clientId) {
		List<ClientIdentification> identification = null;
		try {
			identification = dao.getClientIdentification(clientId);
		}
		catch (Exception e) {
			String msg = String.format("Error querying For ClientIdentification with id = %s in the Smallchange database.", clientId);
			throw new DatabaseException(msg, e);
		}
		return identification;
	}

	@Override
	public int insertClientIdentification(String type, String value, String clientId) {
		int count = 0;
		try {
			count = dao.insertClientIdentification(type, value, clientId);
		}
		catch (Exception e) {
			String msg = "Error inserting ClientIdentification in the Smallchange database.";
			throw new DatabaseException(msg, e);
		}
		return count;
	}

}
