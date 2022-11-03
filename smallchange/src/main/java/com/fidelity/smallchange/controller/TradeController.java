package com.fidelity.smallchange.controller;

import java.util.List;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;
import com.fidelity.smallchange.service.TradeService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	
	@GetMapping(value="/tradeActivity", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Trade>> getTradeActivity(Authentication authentication){
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(userDetails.getClientId());
		try {
			List<Trade> tradeHistory = tradeService.getTradeActivityByClientId(userDetails.getClientId());
			if(tradeHistory.size()==0 || tradeHistory == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(tradeHistory);
		}catch(Exception e) {
			throw new ServerErrorException("Error while conencting to DB",e);
		}
	}
	
	@GetMapping(value="/tradeExecution", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> tradeExecution(Authentication authentication, @RequestBody Order order){
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(userDetails.getClientId());
		
		try {
			
		}catch(Exception e) {
			
		}
	}
}
