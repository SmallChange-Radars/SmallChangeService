package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.model.Instrument;
import com.fidelity.smallchange.model.InstrumentPrice;
import com.fidelity.smallchange.model.Price;

@Service
public class InstrumentService {
	static Map<String, Comparator<Price>> comparators = Map.of("instrumentId",
			Comparator.comparing(p -> p.getInstrument().getInstrumentId()), "instrumentDescription",
			Comparator.comparing(p -> p.getInstrument().getInstrumentDescription()), "categoryId",
			Comparator.comparing(p -> p.getInstrument().getCategoryId()), "askPrice",
			Comparator.comparing(p -> p.getAskPrice()), "minQuantity",
			Comparator.comparing(p -> p.getInstrument().getMinQuantity()), "maxQuantity",
			Comparator.comparing(p -> p.getInstrument().getMaxQuantity()));

	@Autowired
	private FMTSRestClient fmts;

	public Pair<Integer, List<InstrumentPrice>> getInstrumentPrices(String q, Integer _page, Integer _limit,
			String categoryId, String _sort, String _order) throws JsonProcessingException {

		System.out.println(q + " " + _page + " " + _limit + " " + categoryId + " " + _sort + " " + _order);
		List<InstrumentPrice> result = new ArrayList<>();
		try {
			List<Price> prices = fmts.getPrices("");

			Stream<Price> priceStream = prices.stream();

			// search query
			if (q != null && !q.equals(""))
				priceStream = priceStream.filter(p -> p.toCheckString().toUpperCase().contains(q.toUpperCase()));

			// filter by category
			if (categoryId != null)
				priceStream = priceStream.filter(
						p -> p.getInstrument().getCategoryId().toUpperCase().contains(categoryId.toUpperCase()));

			// after filtering and search query, store size
			prices = priceStream.toList();
			Integer originalSize = prices.size();
			priceStream = prices.stream();
			
			// should sort before pagination
			if (_sort != null && _order != null) {
				if (_order.equals("asc"))
					priceStream = priceStream.sorted(comparators.get(_sort));
				else if (_order.equals("desc"))
					priceStream = priceStream.sorted(comparators.get(_sort).reversed());
			}

			// pagination and conversion to DTO
			result = priceStream.skip(_page != null ? (_page - 1) * _limit : 0)
					.limit(_limit != null ? _limit : originalSize).map(p -> {
						return new InstrumentPrice(p.getInstrument().getInstrumentId(),
								p.getInstrument().getInstrumentDescription(), p.getInstrument().getExternalIdType(),
								p.getInstrument().getExternalId(), p.getInstrument().getCategoryId(),
								p.getInstrument().getMinQuantity(), p.getInstrument().getMaxQuantity(), p.getBidPrice(),
								p.getAskPrice());
					}).toList();
			return new Pair<Integer, List<InstrumentPrice>>(originalSize, result);
		} catch (Exception e) {
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
