package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import com.fidelity.smallchange.model.Portfolio;

public interface PortfolioMapper {
	List<Portfolio> getAllPortfolios(); 
	List<Portfolio> getPortfolioByClientId(String clientId); 
	void insertPortfolio(Portfolio portfolio);
	void updatePortfolio(Portfolio portfolio);
}
