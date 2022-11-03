package com.fidelity.smallchange.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.smallchange.integration.mapper.ClientPreferencesMapper;
import com.fidelity.smallchange.model.ClientPreferences;

@Repository
public class ClientPreferencesDaoMyBatisImpl implements ClientPreferencesDao{

	@Autowired
	ClientPreferencesMapper mapper;
	
	@Override
	public ClientPreferences getClientPreferences(String id) {
		ClientPreferences preferences = mapper.getClientPreferences(id);
		return preferences;
	}

	@Override
	public int insertClientPreferences(ClientPreferences clientPreferences) {
		int count = mapper.insertClientPreferences(clientPreferences);
		return count;
	}

	@Override
	public int updateClientPreferences(ClientPreferences clientPreferences) {
		int count = mapper.updateClientPreferences(clientPreferences);
		return count;
	}

}
