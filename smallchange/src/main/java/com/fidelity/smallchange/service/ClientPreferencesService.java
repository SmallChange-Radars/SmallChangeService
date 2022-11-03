package com.fidelity.smallchange.service;

import com.fidelity.smallchange.model.ClientPreferences;

public interface ClientPreferencesService {

	ClientPreferences getClientPreferencesById(String clientId);
	int insertClientPreferences(ClientPreferences preferences);
	int updateClientPreferences(ClientPreferences preferences);
}
