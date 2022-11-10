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
//import com.fidelity.smallchange.model.Price;
import com.fidelity.smallchange.model.Price;

@Service
public class PortfolioServiceImpl implements PortfolioService {
	
	@Autowired 
	PortfolioDaoMyBatisImpl dao;
	
	@Autowired
	private FMTSRestClient fmts;
	
//	@Autowired
//	private InstrumentService instrumentService;
	
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

//	@Override
//	public BigDecimal getPortfolioSummaryValue(String clientId) {
//		/*
//		 * BigDecimal summary = BigDecimal.ZERO; for(Portfolio p:
//		 * dao.getPortfolioByClientId(clientId)) { summary = summary.add(p.getValue());
//		 * } return summary.setScale(2, RoundingMode.HALF_EVEN);
//		 */
//		return dao.getPortfolioSummary(clientId);
//	}
//	
//	@Override
//	public BigDecimal getPortfolioSummaryGains(String clientId) throws JsonProcessingException {
//		List<Portfolio> portfolios = dao.getPortfolioByClientId(clientId);
//		BigDecimal totalGains = BigDecimal.ZERO;
//		for (Portfolio p: portfolios) {
//			BigDecimal currentPrice = instrumentService.getAskPrice(p.getInstrumentId()).setScale(2, RoundingMode.HALF_EVEN);
//			BigDecimal gains = currentPrice.multiply(new BigDecimal(p.getQuantity()).setScale(2, RoundingMode.HALF_EVEN)).subtract(p.getValue()).setScale(2, RoundingMode.HALF_EVEN);
//			totalGains = totalGains.add(gains);
//		}
////		return cps;
//		return totalGains;
//	}
	
	@Override
	public Boolean checkAvailableQuantity(String clientId, String instrumentId, int quantity) {
		Portfolio p = dao.getPortfolioByClientIdAndInstrumentId(clientId, instrumentId);
		if(quantity >= 0 && quantity <= p.getQuantity()) {
			return true;
		}
		return false;
	}
	
	private BigDecimal getAskPrice(String instrumentId, List<Price> prices) throws JsonProcessingException {
		try {

			BigDecimal askPrice = BigDecimal.ZERO;
			for (Price p : prices) {
				if (p.getInstrument().getInstrumentId().equals(instrumentId)) {
					askPrice = p.getAskPrice();
					break;
				}
			}
			return askPrice;
		} catch (Exception e) {
			throw new IllegalArgumentException("InstrumentId doesn't exist");
		}
	}
	
	@Override
	public Pair<List<ClientPortfolio>, List<BigDecimal>> getClientPortfolio(String clientId) throws JsonProcessingException {
		List<Portfolio> portfolios = dao.getPortfolioByClientId(clientId);
		List<ClientPortfolio> cps = new ArrayList<>();
		BigDecimal totalGains = BigDecimal.ZERO;
		BigDecimal totalValue = BigDecimal.ZERO;
		List<Price> prices = fmts.getPrices("");
		for (Portfolio p: portfolios) {
			BigDecimal currentPrice = this.getAskPrice(p.getInstrumentId(), prices).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal gains = currentPrice.multiply(new BigDecimal(p.getQuantity()).setScale(2, RoundingMode.HALF_EVEN)).subtract(p.getValue()).setScale(2, RoundingMode.HALF_EVEN);
			double returns = gains.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			returns = returns / p.getValue().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			ClientPortfolio cp = new ClientPortfolio(p.getClientId(), p.getInstrumentId(), 
					p.getQuantity(), p.getValue(), currentPrice, gains, returns);
			cps.add(cp);
			totalGains = totalGains.add(gains).setScale(2, RoundingMode.HALF_EVEN);
			totalValue = totalValue.add(p.getValue()).setScale(2, RoundingMode.HALF_EVEN);
		}
		Pair<List<ClientPortfolio>, List<BigDecimal>> pair = 
				new Pair<List<ClientPortfolio>, List<BigDecimal>>(cps, List.of(totalValue, totalGains));
		return pair;
	}

}
