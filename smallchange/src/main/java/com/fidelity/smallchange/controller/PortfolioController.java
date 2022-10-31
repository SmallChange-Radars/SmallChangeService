package com.fidelity.smallchange.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.service.PortfolioService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
public class PortfolioController {

	@Autowired
	private final PortfolioService portfolioService;

	public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@GetMapping(path = "portfolio")
	public List<Portfolio> getAllPortfolios(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication
                .getPrincipal();
		System.out.println(userDetails.getClientId());
		return portfolioService.getAllPortfolios();
	}

	@GetMapping(path = "portfolio/{clientId}")
	public List<Portfolio> getPortfolioByClientId(@PathVariable String clientId) {
		return portfolioService.getPortfolioByClientId(clientId);
	}

	@PostMapping(path = "insertportfolio")
	public void insertPortfolio(@RequestBody Portfolio portfolio) {
		portfolioService.insertPortfolio(portfolio);
	}

	@GetMapping(path = "portfolio/{clientId}/summary")
	public BigDecimal getPortfolioSummary(@PathVariable String clientId) {
		return portfolioService.getPortfolioSummary(clientId);
	}
}
