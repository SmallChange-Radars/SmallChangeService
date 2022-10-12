package com.fidelity.smallchange.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fidelity.smallchange.integration.DatabaseException;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.Country;

public class ClientDaoOracleImpl implements ClientDao {
	
	private DataSource dataSource;
	private final Logger logger = LoggerFactory.getLogger(ClientDaoOracleImpl.class);
	
	private final String queryForClients = """
			SELECT c.clientId, c.email, c.dob, c.country, c.postalCode, ci.type, ci.value 
			FROM client c
			LEFT JOIN clientIdentification ci ON c.client_id = ci.client_id
			ORDER BY c.client_id
			""";
	
	private final String insertClient = """
			INSERT INTO client(clientId, email, dob, country, postalCode)
			VALUES(?, ?, ?, ?, ?)
			""";
	
	private final String insertClientIdentification = """
			INSERT INTO clientIdentification(type, value, clientId)
			VALUES(?, ?, ?)
			""";

	@Override
	public List<Client> getClients() {
		List<Client> clients = null;
		try(Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(queryForClients)) {
			clients = getAndHandleResults(stmt);
		} catch (SQLException e) {
			logger.error("Cannot execute getClients: ", e);
			throw new DatabaseException("Cannot execute getClients", e);
		}
		return clients;
	}
	
	private List<Client> getAndHandleResults(PreparedStatement stmt) throws SQLException {
		List <Client> clients = new ArrayList<>();
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			String clientId = rs.getString("clientId");
			String email = rs.getString("email");
			String dateOfBirth = rs.getString("dateOfBirth");
			Country country = Country.of(rs.getString("country"));
			String postalCode = rs.getString("postalCode");
			String type = rs.getString("type");
			String value = rs.getString("value");
			ClientIdentification clientIdentification = new ClientIdentification(type, value);
			
			Client client = new Client(clientId, email, dateOfBirth, country, postalCode, clientIdentification);
			clients.add(client);
		}
		return clients;
	}

	@Override
	public void insertClient(Client client) {
		Objects.requireNonNull(client);
		try(Connection connection = dataSource.getConnection()) {
			insertClient(client, connection);
			insertIdentification(client, connection);
		} catch (SQLException e) {
			logger.error("Cannot insert client with Client Id: " + client.getClientId(), e);
			throw new DatabaseException("Cannot insert client with Client Id: " + client.getClientId(), e);
		}		
	}
	
	private void insertClient(Client client, Connection connection) throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(insertClient)) {
			stmt.setString(1, client.getClientId());
			stmt.setString(2, client.getEmail());
			stmt.setString(3, client.getDateOfBirth());
			stmt.setString(4, client.getCountry().getCode());
			stmt.setString(5, client.getPostalCode());
			
			stmt.executeUpdate();
		}
	}
	
	private void insertIdentification(Client client, Connection connection) throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(insertClientIdentification)) {
			ClientIdentification ci = client.getClientIdentification().get(0);
			stmt.setString(1, ci.getType());
			stmt.setString(2, ci.getValue());
			stmt.setString(3, client.getClientId());
			
			stmt.executeUpdate();
		}
	}

}
