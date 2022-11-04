package com.fidelity.smallchange.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Portfolio;

@Repository
public class PortfolioDaoMyBatisImpl implements PortfolioDao {

	@Autowired
	PortfolioMapper mapper;
	
	
	@Override
	public List<Portfolio> getAllPortfolios() {
		return mapper.getAllPortfolios();
	}


	@Override
	public List<Portfolio> getPortfolioByClientId(String clientId) {
		return mapper.getPortfolioByClientId(clientId);
	}


	@Override
	public void insertPortfolio(Portfolio portfolio) {
		mapper.insertPortfolio(portfolio);
	}


	@Override
	public void updatePortfolio(Portfolio portfolio) {
		mapper.updatePortfolio(portfolio.getClientId(), portfolio.getInstrumentId(), portfolio.getQuantity(), portfolio.getValue());		
	}


	@Override
	public void deletePortfolioByClientIdAndInstrumentId(String clientId, String instrumentId) {
		mapper.deletePortfolioByClientIdAndInstrumentId(clientId, instrumentId);
	}


	@Override
	public Portfolio getPortfolioByClientIdAndInstrumentId(String clientId, String instrumentId) {
		mapper.getPortfolioByClientIdAndInstrumentId(clientId, instrumentId);
		return null;
	}	

	
}
