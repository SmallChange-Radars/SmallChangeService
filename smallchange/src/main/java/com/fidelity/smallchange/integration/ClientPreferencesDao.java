package com.fidelity.smallchange.integration;
import com.fidelity.smallchange.model.ClientPreferences;

public interface ClientPreferencesDao {
	ClientPreferences getClientPreferences(String id);
	int insertClientPreferences(ClientPreferences clientPreferences);
	int updateClientPreferences(ClientPreferences clientPreferences);
}
