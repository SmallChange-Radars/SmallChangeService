package com.fidelity.smallchange.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.model.ClientPortfolio;
import com.fidelity.smallchange.service.PortfolioService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class PortfolioController {

	@Autowired
	private Logger logger;
	
	@Autowired
	private final PortfolioService portfolioService;

	public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	/*
	 * @GetMapping(path = "portfolio/all") public ResponseEntity<List<Portfolio>>
	 * getAllPortfolios(Authentication authentication) { try { List<Portfolio>
	 * allPortfolios = portfolioService.getAllPortfolios();
	 * if(allPortfolios.size()==0 || allPortfolios == null) { return
	 * ResponseEntity.noContent().build(); } return
	 * ResponseEntity.ok(allPortfolios); } catch (Exception e) { throw new
	 * ServerErrorException("Error while conencting to DB", e); } }
	 */

	@GetMapping(path = "portfolio")
	public ResponseEntity<List<ClientPortfolio>> getPortfolioByClientId(@AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
//		return portfolioService.getPortfolioByClientId(userDetails.getClientId());
		logger.debug("Getting Portfolio for Client with clientId = " + userDetails.getClientId());
		try {
			List<ClientPortfolio> portfolios = portfolioService.getClientPortfolio(userDetails.getClientId()).getFirst();
			
			BigDecimal summaryValue = portfolioService.getClientPortfolio(userDetails.getClientId()).getSecond().get(0);
			BigDecimal summaryGains = portfolioService.getClientPortfolio(userDetails.getClientId()).getSecond().get(1);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Access-Control-Expose-Headers", "totalValue, totalGains");
			responseHeaders.set("totalValue", String.valueOf(summaryValue));
			responseHeaders.set("totalGains", String.valueOf(summaryGains));
			
			if(portfolios.size()==0 || portfolios == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().headers(responseHeaders).body(portfolios);
		} catch (Exception e) {
			logger.error("Exception while getting Portfolio for Client with clientId = " + userDetails.getClientId());
			throw new ServerErrorException("Error while conencting to DB", e);
		}
	}

	/*
	 * @Deprecated
	 * 
	 * @PostMapping(path = "insert/portfolio") public void
	 * insertPortfolio(@RequestBody Portfolio portfolio) {
	 * portfolioService.insertPortfolio(portfolio); }
	 */

//	@GetMapping(path = "portfolio/summary")
//	public ResponseEntity<List<BigDecimal>> getPortfolioSummary(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//		try {
//			BigDecimal summary = portfolioService.getPortfolioSummaryValue(userDetails.getClientId());
//			BigDecimal summaryGains = portfolioService.getPortfolioSummaryGains(userDetails.getClientId());
//			if(summary == null) {
//				return ResponseEntity.noContent().build();
//			}
//			return ResponseEntity.ok(List.of(summary, summaryGains));
//		} catch (Exception e) {
//			throw new ServerErrorException("Error while conencting to DB", e);
//		}
//	}
	
	/*
	 * @Deprecated
	 * 
	 * @GetMapping(path = "portfolio/{clientId}/client") public
	 * List<ClientPortfolio> getClientPortfolio(@PathVariable String clientId)
	 * throws JsonProcessingException { return
	 * portfolioService.getClientPortfolio(clientId); }
	 */
}
