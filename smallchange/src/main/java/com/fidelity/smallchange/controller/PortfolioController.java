package com.fidelity.smallchange.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.model.ClientPortfolio;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.service.PortfolioService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class PortfolioController {

	@Autowired
	private final PortfolioService portfolioService;

	public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@GetMapping(path = "portfolio/all")
	public ResponseEntity<List<Portfolio>> getAllPortfolios(Authentication authentication) {
		try {
			List<Portfolio> allPortfolios = portfolioService.getAllPortfolios();
			if(allPortfolios.size()==0 || allPortfolios == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(allPortfolios);
		} catch (Exception e) {
			throw new ServerErrorException("Error while conencting to DB", e);
		}
	}

	@GetMapping(path = "portfolio")
	public ResponseEntity<List<ClientPortfolio>> getPortfolioByClientId(@AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
//		return portfolioService.getPortfolioByClientId(userDetails.getClientId());
		try {
			List<ClientPortfolio> portfolios = portfolioService.getClientPortfolio(userDetails.getClientId());

			///////
			BigDecimal summaryValue = portfolioService.getPortfolioSummaryValue(userDetails.getClientId());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Access-Control-Expose-Headers", "totalValue");
			responseHeaders.set("totalValue", String.valueOf(summaryValue));
			
			if(portfolios.size()==0 || portfolios == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().headers(responseHeaders).body(portfolios);
		} catch (Exception e) {
			throw new ServerErrorException("Error while conencting to DB", e);
		}
	}

	@Deprecated
	@PostMapping(path = "insert/portfolio")
	public void insertPortfolio(@RequestBody Portfolio portfolio) {
		portfolioService.insertPortfolio(portfolio);
	}

	@GetMapping(path = "portfolio/summary")
	public ResponseEntity<List<BigDecimal>> getPortfolioSummary(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			BigDecimal summary = portfolioService.getPortfolioSummaryValue(userDetails.getClientId());
			BigDecimal summaryGains = portfolioService.getPortfolioSummaryGains(userDetails.getClientId());
			if(summary == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(List.of(summary, summaryGains));
		} catch (Exception e) {
			throw new ServerErrorException("Error while conencting to DB", e);
		}
	}
	
	@Deprecated
	@GetMapping(path = "portfolio/{clientId}/client")
	public List<ClientPortfolio> getClientPortfolio(@PathVariable String clientId) throws JsonProcessingException {
		return portfolioService.getClientPortfolio(clientId);
	}
}
