package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.PortfolioDaoMyBatisImpl;
import com.fidelity.smallchange.model.Portfolio;

@Service
public class PortfolioService {
	
	@Autowired 
	PortfolioDaoMyBatisImpl dao;
	
	public List<Portfolio> getAllPortfolios() {
		return dao.getAllPortfolios();
	}

	public List<Portfolio> getPortfolioByClientId(String clientId) {
		return dao.getPortfolioByClientId(clientId);
	}

	public void insertPortfolio(Portfolio portfolio) {
		dao.insertPortfolio(portfolio);
	}

	public BigDecimal getPortfolioSummary(String clientId) {
		BigDecimal summary = BigDecimal.ZERO;
		for(Portfolio p: dao.getPortfolioByClientId(clientId)) {
			summary = summary.add(p.getValue());
		}
		return summary.setScale(2, RoundingMode.HALF_EVEN);
	}
}
