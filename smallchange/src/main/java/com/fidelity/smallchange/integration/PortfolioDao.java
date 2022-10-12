package com.fidelity.smallchange.integration;

import java.util.List;

import com.fidelity.smallchange.model.Portfolio;

public interface PortfolioDao {
	List<Portfolio> getAllPortfolios(); 
	List<Portfolio> getPortfolioById(String clientId); 
	void insertPortfolio(Portfolio portfolio);
	void updatePortfolio(Portfolio portfolio);
}
