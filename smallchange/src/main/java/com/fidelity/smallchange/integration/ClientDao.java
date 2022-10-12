package com.fidelity.smallchange.integration;

import java.util.List;

import com.fidelity.smallchange.model.Client;

public interface ClientDao {
	List<Client> getClients();
	void insertClient(Client client);
}
