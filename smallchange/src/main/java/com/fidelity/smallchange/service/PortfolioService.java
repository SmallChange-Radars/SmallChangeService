package com.fidelity.smallchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Portfolio;

@Service
public class PortfolioService {
	
	@Autowired 
	PortfolioMapper dao;
	
	public List<Portfolio> getAllPortfolios() {
		return dao.getAllPortfolios();
	}

	public List<Portfolio> getPortfolioByClientId(String clientId) {
		return dao.getPortfolioByClientId(clientId);
	}

	public void insertPortfolio(Portfolio portfolio) {
		dao.insertPortfolio(portfolio);
	}
}
