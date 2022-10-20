package com.fidelity.smallchange.integration;
import com.fidelity.smallchange.model.ClientPreferences;

public interface ClientPreferencesDao {
	ClientPreferences getClientPreferences(String id);
	void insertClientPreferences(ClientPreferences clientPreferences);
	void updateClientPreferences(ClientPreferences clientPreferences);
}
