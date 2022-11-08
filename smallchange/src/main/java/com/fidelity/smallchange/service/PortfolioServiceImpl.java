package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.PortfolioDaoMyBatisImpl;
import com.fidelity.smallchange.model.ClientPortfolio;
import com.fidelity.smallchange.model.Portfolio;
//import com.fidelity.smallchange.model.Price;

@Service
public class PortfolioServiceImpl implements PortfolioService {
	
	@Autowired 
	PortfolioDaoMyBatisImpl dao;
	
	/*
	 * @Autowired private FMTSRestClient fmts;
	 */
	
	@Autowired
	private InstrumentService instrumentService;
	
	String url = "http://localhost:3000/fmts";
	
	@Override
	public List<Portfolio> getAllPortfolios() {
		return dao.getAllPortfolios();
	}

	@Override
	public List<Portfolio> getPortfolioByClientId(String clientId) {
		return dao.getPortfolioByClientId(clientId);
	}

	@Override
	public void insertPortfolio(Portfolio portfolio) {
		dao.insertPortfolio(portfolio);
	}

	@Override
	public BigDecimal getPortfolioSummary(String clientId) {
		BigDecimal summary = BigDecimal.ZERO;
		for(Portfolio p: dao.getPortfolioByClientId(clientId)) {
			summary = summary.add(p.getValue());
		}
		return summary.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	@Override
	public Boolean checkAvailableQuantity(String clientId, String instrumentId, int quantity) {
		Portfolio p = dao.getPortfolioByClientIdAndInstrumentId(clientId, instrumentId);
		if(quantity >= 0 && quantity <= p.getQuantity()) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<ClientPortfolio> getClientPortfolio(String clientId) throws JsonProcessingException {
		List<Portfolio> portfolios = dao.getPortfolioByClientId(clientId);
		List<ClientPortfolio> cps = new ArrayList<>();
		for (Portfolio p: portfolios) {
			BigDecimal currentPrice = instrumentService.getAskPrice(p.getInstrumentId()).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal gains = currentPrice.multiply(new BigDecimal(p.getQuantity()).setScale(2, RoundingMode.HALF_EVEN)).subtract(p.getValue()).setScale(2, RoundingMode.HALF_EVEN);
			double returns = gains.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			returns = returns / p.getValue().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			ClientPortfolio cp = new ClientPortfolio(p.getClientId(), p.getInstrumentId(), 
					p.getQuantity(), p.getValue(), currentPrice, gains, returns);
			cps.add(cp);
		}
		return cps;
	}
}
