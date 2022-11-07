package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.model.Instrument;
import com.fidelity.smallchange.model.Price;

@Service
public class InstrumentService {

	@Autowired
	private FMTSRestClient fmts;
	
//	String url = "http://localhost:3000/fmts";
	
	public Instrument getInstrumentFromInstrumentId (String instrumentId) throws JsonProcessingException {
		try {

			List<Instrument> instrumentList = fmts.getInstruments("");
			Instrument instrument = null;
			for (Instrument inst: instrumentList) {
				if(inst.getInstrumentId().equals(instrumentId)) {
					instrument = inst;
				}
			}
			return instrument;
		}
		catch (Exception e) {
			throw new IllegalArgumentException("InstrumentId not found");
		}
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
	
	public Boolean checkMinAndMaxQuantity(String instrumentId, int quantity) throws JsonProcessingException {
		Instrument inst = this.getInstrumentFromInstrumentId(instrumentId);
		if(quantity <= inst.getMaxQuantity() && quantity >= inst.getMinQuantity()) {
			return true;
		}
		return false;		
	}
	
}
