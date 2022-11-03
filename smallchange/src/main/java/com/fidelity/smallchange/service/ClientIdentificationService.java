package com.fidelity.smallchange.service;

import java.util.List;

import com.fidelity.smallchange.model.ClientIdentification;

public interface ClientIdentificationService {

	List<ClientIdentification> getClientIdentification(String clientId);
	int insertClientIdentification(String type, String value, String clientId);
}
