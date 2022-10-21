package com.fidelity.smallchange.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import com.fidelity.smallchange.model.ClientPreferences;

public class ClientPreferencesDaoOracleImpl implements ClientPreferencesDao {

	protected DataSource dataSource;

	public ClientPreferencesDaoOracleImpl(DataSource datasource) {
		this.dataSource = datasource;
	}

	private final String queryForClintPreferences = """
			select * from preferences
			where clientId = ?
			""";

	private final String insertClientPreferences = """
			INSERT
			INTO preferences
			(clientId,investmentPurpose,riskTolerance,incomeCategory,lengthOfInvestment)
			VALUES (?, ?, ?, ?, ?)
			""";

	private final String updateClientPreferences = """
			UPDATE preferences
			SET investmentPurpose = ?,riskTolerance = ?,
			incomeCategory = ?,lengthOfInvestment = ?
			WHERE clientId = ?
			""";

	@Override
	public ClientPreferences getClientPreferences(String id) {
		List<ClientPreferences> cfs = new ArrayList<>();
		/*
		 * try (Connection connection = dataSource.getConnection(); PreparedStatement
		 * stmt = connection.prepareStatement(queryForClintPreferences)) {
		 * stmt.setString(2, id); ResultSet rs = stmt.executeQuery(); while (rs.next())
		 * { String clientId = rs.getString("clientId"); String investmentPurpose =
		 * rs.getString("investmentPurpose"); String riskTolerance =
		 * rs.getString("riskTolerance"); String incomeCategory =
		 * rs.getString("incomeCategory"); String lengthOfInvestment =
		 * rs.getString("incomeCategory");
		 * 
		 * ClientPreferences cf = new ClientPreferences(clientId, investmentPurpose,
		 * riskTolerance, incomeCategory, lengthOfInvestment); cfs.add(cf); } } catch
		 * (SQLException e) { throw new
		 * DatabaseException("Cannot execute queryForAllCars", e); }
		 */
		return cfs.size() > 0 ? cfs.get(0) : null;
	}

	@Override
	public void insertClientPreferences(ClientPreferences clientPreferences) {
		/*
		 * try (Connection connection = dataSource.getConnection(); PreparedStatement
		 * stmt = connection.prepareStatement(insertClientPreferences)) {
		 * stmt.setString(1, clientPreferences.getClientId()); stmt.setString(2,
		 * clientPreferences.getInvestmentPurpose()); stmt.setString(3,
		 * clientPreferences.getRiskTolerance().getName()); stmt.setString(4,
		 * clientPreferences.getIncomeCategory().getName()); stmt.setString(5,
		 * clientPreferences.getInvestmentLength().getName()); stmt.executeUpdate(); }
		 * catch (SQLException e) { throw new
		 * DatabaseException("Unable to insert ClientPreferences with id=" +
		 * clientPreferences.getClientId(), e); }
		 */
	}

	@Override
	public void updateClientPreferences(ClientPreferences clientPreferences) {
		/*
		 * try (Connection connection = dataSource.getConnection(); PreparedStatement
		 * stmt = connection.prepareStatement(updateClientPreferences)) {
		 * 
		 * stmt.setString(1, clientPreferences.getInvestmentPurpose());
		 * stmt.setString(2, clientPreferences.getRiskTolerance().getName());
		 * stmt.setString(3, clientPreferences.getIncomeCategory().getName());
		 * stmt.setString(4, clientPreferences.getInvestmentLength().getName());
		 * stmt.setString(5, clientPreferences.getClientId()); stmt.executeUpdate(); }
		 * catch (SQLException e) { throw new
		 * DatabaseException("Unable to Update ClientPreferences with id=" +
		 * clientPreferences.getClientId(), e); }
		 */
	}
}
