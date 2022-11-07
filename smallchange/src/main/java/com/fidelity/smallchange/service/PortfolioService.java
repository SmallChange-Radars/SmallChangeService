package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.model.ClientPortfolio;
import com.fidelity.smallchange.model.Portfolio;

public interface PortfolioService {
	public List<Portfolio> getAllPortfolios();
	public List<Portfolio> getPortfolioByClientId(String clientId);
	public void insertPortfolio(Portfolio portfolio);
	public BigDecimal getPortfolioSummary(String clientId);
	public Boolean checkAvailableQuantity(String clientId, String instrumentId, int quantity);
	public List<ClientPortfolio> getClientPortfolio(String clientId) throws JsonProcessingException;
}
