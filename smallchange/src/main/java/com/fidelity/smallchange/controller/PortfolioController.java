package com.fidelity.smallchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Portfolio;

@RestController
public class PortfolioController {
	@Autowired 
	PortfolioMapper dao;
	
	@GetMapping(path = "allportfolios")
	public List<Portfolio> getAllPortfolios() {
		return dao.getAllPortfolios();
	}
	
	@GetMapping(path = "{clientId}/portfolio")
	public List<Portfolio> getPortfolioByClientId(@PathVariable String clientId) {
		return dao.getPortfolioByClientId(clientId);
	}
	
	@PostMapping(path = "insertportfolio")
	public void insertPortfolio(@RequestBody Portfolio portfolio) {
		dao.insertPortfolio(portfolio);
	}
	
}
