package com.fidelity.smallchange.integration;

import java.math.BigDecimal;
import java.util.List;

import com.fidelity.smallchange.model.Portfolio;

public interface PortfolioDao {
	List<Portfolio> getAllPortfolios(); 
	List<Portfolio> getPortfolioByClientId(String clientId); 
	BigDecimal getPortfolioSummary(String clientId);
	Portfolio getPortfolioByClientIdAndInstrumentId(String clientId, String instrumentId);
	void insertPortfolio(Portfolio portfolio);
	void updatePortfolio(Portfolio portfolio);
	void deletePortfolioByClientIdAndInstrumentId(String clientId, String instrumentId);
}
