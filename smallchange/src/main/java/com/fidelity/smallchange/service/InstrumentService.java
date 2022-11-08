package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.model.Instrument;
import com.fidelity.smallchange.model.InstrumentPrice;
import com.fidelity.smallchange.model.Price;

@Service
public class InstrumentService {
	static Map<String, Comparator<InstrumentPrice>> comparators = Map.of("instrumentId",
			Comparator.comparing(InstrumentPrice::getInstrumentId), "instrumentDescription",
			Comparator.comparing(InstrumentPrice::getInstrumentDescription), "categoryId",
			Comparator.comparing(InstrumentPrice::getCategoryId), "askPrice",
			Comparator.comparing(InstrumentPrice::getAskPrice), "minQuantity",
			Comparator.comparing(InstrumentPrice::getMinQuantity), "maxQuantity",
			Comparator.comparing(InstrumentPrice::getMaxQuantity));

	@Autowired
	private FMTSRestClient fmts;

	public Pair<Integer, List<InstrumentPrice>> getInstrumentPrices(String q, Integer _page, Integer _limit,
			String categoryId, String _sort, String _order) throws JsonProcessingException {

		System.out.println(q + " " + _page + " " + _limit + " " + categoryId + " " + _sort + " " + _order);
		List<InstrumentPrice> result = new ArrayList<>();
		try {
			List<Price> prices = fmts.getPrices("");

			// only where both conditions satisfy not both and conversion to DTO
			for (Price p : prices) {
				// search query
				if ((q == null || q.equals("")
						|| (q != null && !q.equals("") && p.toCheckString().toUpperCase().contains(q.toUpperCase())))
						// filter by category
						&& (categoryId == null || (categoryId != null && p.getInstrument().getCategoryId().toUpperCase()
								.contains(categoryId.toUpperCase())))) {
					result.add(new InstrumentPrice(p.getInstrument().getInstrumentId(),
							p.getInstrument().getInstrumentDescription(), p.getInstrument().getExternalIdType(),
							p.getInstrument().getExternalId(), p.getInstrument().getCategoryId(),
							p.getInstrument().getMinQuantity(), p.getInstrument().getMaxQuantity(), p.getBidPrice(),
							p.getAskPrice()));
				}
			}

			// after filtering and search query, store size, to return as header
			Integer originalSize = result.size();

			// should sort before pagination
			if (_sort != null && _order != null && originalSize > 1) {
				if (_order.equals("asc"))
					result.sort(comparators.get(_sort));
				else if (_order.equals("desc"))
					result.sort(comparators.get(_sort).reversed());
			}

			// pagination
			if (_page != null && _limit != null && originalSize > 1) {
				result = result.subList((_page - 1) * _limit, Math.min(_page * _limit, originalSize));
			}
			
			return new Pair<Integer, List<InstrumentPrice>>(originalSize, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Instruments cant be queried from fmts");
		}
	}

	public Instrument getInstrumentFromInstrumentId(String instrumentId) throws JsonProcessingException {
		try {

			List<Instrument> instrumentList = fmts.getInstruments("");
			Instrument instrument = null;
			for (Instrument inst : instrumentList) {
				if (inst.getInstrumentId().equals(instrumentId)) {
					instrument = inst;
					break;
				}
			}
			return instrument;
		} catch (Exception e) {
			throw new IllegalArgumentException("InstrumentId not found");
		}
	}

	public BigDecimal getAskPrice(String instrumentId) throws JsonProcessingException {
		List<Price> prices = fmts.getPrices("");
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

	public BigDecimal getBidPrice(String instrumentId) throws JsonProcessingException {
		List<Price> prices = fmts.getPrices("");
		try {

			BigDecimal bidPrice = BigDecimal.ZERO;
			for (Price p : prices) {
				if (p.getInstrument().getInstrumentId().equals(instrumentId)) {
					bidPrice = p.getBidPrice();
					break;
				}
			}
			return bidPrice;
		} catch (Exception e) {
			throw new IllegalArgumentException("InstrumentId doesn't exist");
		}
	}

	public Boolean checkMinAndMaxQuantity(String instrumentId, int quantity) throws JsonProcessingException {
		Instrument inst = this.getInstrumentFromInstrumentId(instrumentId);
		if (quantity <= inst.getMaxQuantity() && quantity >= inst.getMinQuantity()) {
			return true;
		}
		return false;
	}

}
