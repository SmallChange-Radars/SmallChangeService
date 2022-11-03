package com.fidelity.smallchange.integration;

import java.util.List;

import com.fidelity.smallchange.model.ClientIdentification;

public interface ClientIdentificationDao {

	List<ClientIdentification> getClientIdentification(String clientId);
	int insertClientIdentification(String type, String value, String clientId);
	int updateClientIdentification(String type, String value, String clientId);
}
