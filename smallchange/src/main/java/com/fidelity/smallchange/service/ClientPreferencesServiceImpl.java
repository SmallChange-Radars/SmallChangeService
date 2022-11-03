package com.fidelity.smallchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.ClientPreferencesDao;
import com.fidelity.smallchange.integration.DatabaseException;
import com.fidelity.smallchange.model.ClientPreferences;

@Service
public class ClientPreferencesServiceImpl implements ClientPreferencesService{

	@Autowired
	ClientPreferencesDao dao;
	
	@Override
	public ClientPreferences getClientPreferencesById(String clientId) {
		ClientPreferences preferences = null;
		try {
			preferences = dao.getClientPreferences(clientId);
		}
		catch(Exception e) {
			String msg = String.format("Error querying For ClientPreferences with id = %s in the Smallchange database.", clientId);
			throw new DatabaseException(msg, e);
		}
		return preferences;
	}

	@Override
	public int insertClientPreferences(ClientPreferences preferences, String clientId) {
		int count = 0;
		try {
			count = dao.insertClientPreferences(preferences);
		}
		catch(Exception e) {
			String msg = "Error inserting ClientPreferences in the Smallchange database.";
			throw new DatabaseException(msg, e);
		}
		return count;
	}

	@Override
	public int updateClientPreferences(ClientPreferences preferences, String clientId) {
		int count = 0;
		try {
			count = dao.updateClientPreferences(preferences);
		}
		catch(Exception e) {
			String msg = "Error updating ClientPreferences in the Smallchange database.";
			throw new DatabaseException(msg, e);
		}
		return count;
	}

}
