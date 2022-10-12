package com.fidelity.smallchange.integration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.fidelity.smallchange.model.Portfolio;

public class PortfolioDaoOracleImpl implements PortfolioDao {

	protected DataSource dataSource; 
	
	public PortfolioDaoOracleImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Portfolio> getAllPortfolios() {
		String queryAllPortfolios = """
			SELECT clientId, instrumentId, quantity, value
			FROM portfolio
			""";
		
		List<Portfolio> portfolios = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(queryAllPortfolios)) {
			portfolios = getAndHandleResults(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return portfolios;
	}	

	@Override
	public List<Portfolio> getPortfolioById(String clientId) {
		String queryAllPortfolios = """
				SELECT clientId, instrumentId, quantity, value
				FROM portfolio
				WHERE clientId = ?
				""";
			
			List<Portfolio> portfolios = new ArrayList<>();
			try (Connection connection = dataSource.getConnection();
					PreparedStatement stmt = connection.prepareStatement(queryAllPortfolios)) {
				stmt.setString(1, clientId);
				portfolios = getAndHandleResults(stmt);
			} catch (SQLException e) {
				throw new DatabaseException("Cannot query clientId " + clientId);
			}
			
			return portfolios;
	}

	@Override
	public void insertPortfolio(Portfolio portfolio) {
		String insertPortfolio = """
				INSERT 
				INTO portfolio (clientId, instrumentId, quantity, value)
				VALUES (?, ?, ?, ?)
				""";
		try(Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(insertPortfolio)) {
			stmt.setString(1, portfolio.getClientId());
			stmt.setString(2, portfolio.getInstrumentId());
			stmt.setInt(3, portfolio.getQuantity());
			stmt.setBigDecimal(4, portfolio.getValue().setScale(2));
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updatePortfolio(Portfolio portfolio) {
		
		String updatePortfolio = """
				UPDATE portfolio 
				SET instrumentId = ?, quantity = ?, value = ?,
				WHERE clientId = ?
				""" ;
		try(Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(updatePortfolio)) {
				stmt.setString(1, portfolio.getInstrumentId());
				stmt.setInt(2, portfolio.getQuantity());
				stmt.setBigDecimal(3, portfolio.getValue().setScale(2));
				stmt.setString(4, portfolio.getClientId());
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	/* Utility */
	private List<Portfolio> getAndHandleResults(PreparedStatement stmt) throws SQLException {
		List<Portfolio> portfolios = new ArrayList<>();
		
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			String clientId = rs.getString("clientId");
			String instrumentId = rs.getString("instrumentId");
			int quantity = rs.getInt("quantity");
			BigDecimal value = rs.getBigDecimal("value");
			
			Portfolio portfolio = new Portfolio(clientId, instrumentId, quantity, value);

			portfolios.add(portfolio);
		}
		return portfolios;
	}


}
