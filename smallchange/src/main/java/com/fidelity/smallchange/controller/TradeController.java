package com.fidelity.smallchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fidelity.smallchange.model.MessageResponse;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Trade;
import com.fidelity.smallchange.service.TradeService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@GetMapping(value = "/tradeActivity", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Trade>> getTradeActivity(@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam(required = false) String q, @RequestParam(required = false) Integer _page,
			@RequestParam(required = false) Integer _limit, @RequestParam(required = false) String _sort,
			@RequestParam(required = false) String _order) {
		try {

			List<Trade> tradeHistory = tradeService.getTradeActivityByClientId(userDetails.getClientId(), q, _page,
					_limit, _sort, _order);
			int totalTradesCount = tradeService.totalTradesByClientId(userDetails.getClientId());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Access-Control-Expose-Headers", "X-Total-Count");
			responseHeaders.set("X-Total-Count", String.valueOf(totalTradesCount));
			if (tradeHistory.size() == 0 || tradeHistory == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().headers(responseHeaders).body(tradeHistory);
		} catch (Exception e) {
			throw new ServerErrorException("Error while conencting to DB", e);
		}
	}

	@PostMapping(value = "/tradeExecution", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> tradeExecution(@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestBody Order order) {
		try {
			if (tradeService.tradeExecution(order, userDetails.getClientId())) {
				return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Trade executed successfully"));
			}
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Price mismatch or unavailable resources to execute Trade."));
		} catch (Exception e) {
			throw new ServerErrorException("Error while conencting to DB", e);
		}

	}
}
