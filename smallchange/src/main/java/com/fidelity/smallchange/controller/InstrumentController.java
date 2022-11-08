package com.fidelity.smallchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fidelity.smallchange.model.InstrumentPrice;
import com.fidelity.smallchange.service.InstrumentService;
import com.fidelity.smallchange.service.Pair;

@RestController
@RequestMapping("/api")
public class InstrumentController {
	private static final String DB_ERROR_MSG = "Error communicating with the Smallchange database";

	@Autowired
	private InstrumentService service;

	@GetMapping(path = "instruments-prices")
	public ResponseEntity<List<InstrumentPrice>> getInstrumentPrices(@RequestParam(required = false) String q,
			@RequestParam(required = false) Integer _page, @RequestParam(required = false) Integer _limit,
			@RequestParam(required = false) String categoryId, @RequestParam(required = false) String _sort,
			@RequestParam(required = false) String _order) {
		List<InstrumentPrice> instrumentPrices;
		ResponseEntity<List<InstrumentPrice>> responseEntity;
		try {
			Pair<Integer, List<InstrumentPrice>> sizeInstrumentPrices = service.getInstrumentPrices(q, _page, _limit,
					categoryId, _sort, _order);
			instrumentPrices = sizeInstrumentPrices.getSecond();
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Access-Control-Expose-Headers", "X-Total-Count");

			if (instrumentPrices != null && instrumentPrices.size() > 0) {
				responseHeaders.set("X-Total-Count", String.valueOf(sizeInstrumentPrices.getFirst()));
				responseEntity = ResponseEntity.ok().headers(responseHeaders).body(instrumentPrices);
			} else {
				// body and HTTP status 204
				responseHeaders.set("X-Total-Count", String.valueOf(0));
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
	}

}
