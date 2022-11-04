package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.PortfolioDaoMyBatisImpl;
import com.fidelity.smallchange.model.ClientPortfolio;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Price;

@Service
public class PortfolioService {
	
	@Autowired 
	PortfolioDaoMyBatisImpl dao;
	
	@Autowired
	private FMTSRestClient fmts;
	
	String url = "http://localhost:3000/fmts";
	
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
	
	public BigDecimal getAskPrice(String instrumentId) throws JsonProcessingException {
		List<Price> prices = fmts.getPrices("");
		try {

			BigDecimal askPrice = BigDecimal.ZERO;
			for (Price p: prices) {
				if (p.getInstrument().getInstrumentId().equals(instrumentId)) {
					askPrice = p.getAskPrice();
				}
			}		
			return askPrice;
		}
		catch (Exception e) {
			throw new IllegalArgumentException("InstrumentId doesn't exist");
		}
	}
	
	public BigDecimal getBidPrice(String instrumentId) throws JsonProcessingException {
		List<Price> prices = fmts.getPrices("");
		try {

			BigDecimal bidPrice = BigDecimal.ZERO;
			for (Price p: prices) {
				if (p.getInstrument().getInstrumentId().equals(instrumentId)) {
					bidPrice = p.getBidPrice();
				}
			}		
			return bidPrice;
		}
		catch (Exception e) {
			throw new IllegalArgumentException("InstrumentId doesn't exist");
		}
	}
	
	public List<ClientPortfolio> getClientPortfolio(String clientId) throws JsonProcessingException {
		List<Portfolio> portfolios = dao.getPortfolioByClientId(clientId);
		List<ClientPortfolio> cps = new ArrayList<>();
		for (Portfolio p: portfolios) {
			BigDecimal currentPrice = this.getAskPrice(p.getInstrumentId()).setScale(2, RoundingMode.HALF_EVEN);
			System.out.println(currentPrice);
			BigDecimal gains = currentPrice.multiply(new BigDecimal(p.getQuantity()).setScale(2, RoundingMode.HALF_EVEN)).subtract(p.getValue()).setScale(2, RoundingMode.HALF_EVEN);
			double returns = gains.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			returns = returns / p.getValue().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			System.out.println(returns);
			ClientPortfolio cp = new ClientPortfolio(p.getClientId(), p.getInstrumentId(), 
					p.getQuantity(), p.getValue(), currentPrice, gains, returns);
			cps.add(cp);
		}
		return cps;
	}
}
