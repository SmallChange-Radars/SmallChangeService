package com.fidelity.smallchange.integration;

import java.util.List;

import com.fidelity.smallchange.model.Portfolio;

public interface PortfolioDao {
	List<Portfolio> getAllPortfolios(); 
	List<Portfolio> getPortfolioByClientId(String clientId); 
	void insertPortfolio(Portfolio portfolio);
	void updatePortfolio(Portfolio portfolio);
	void deletePortfolioByClientIdAndInstrumentId(String clientId, String instrumentId);
}
