package com.fidelity.smallchange.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.PortfolioDao;
import com.fidelity.smallchange.integration.TradeOrderDao;
import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.Order;
import com.fidelity.smallchange.model.Portfolio;
import com.fidelity.smallchange.model.Token;
import com.fidelity.smallchange.model.Trade;

import oracle.net.aso.f;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeOrderDao dao;

	@Autowired
	private ClientService clientService;

	@Autowired
	private PortfolioDao portfolioDao;

	@Autowired
	private FMTSRestClient fmtsRestClient;

	public List<Trade> getTradeActivityByClientId(String clientId,String q,String _category,int _page,int _limit,String _sort,String _order) throws Exception {

		try {
			int offset=(_page-1)*_limit;
			q="%"+q+"%";
			_category="%"+_category+"%";
			return dao.getTradeActivityByClient(clientId,q,_category,_sort,_order,offset,_limit);

		} catch (Exception e) {
			throw new Exception("Error while connecting to DB");
		}
	}

	@Override
	public boolean tradeExecution(Order order, String clientId) throws Exception {
		try {
			Token token = clientService.getToken(clientId);
			String orderId = UUID.randomUUID().toString();
			order.setClientId(clientId);
			order.setToken(token.getToken());
			order.setOrderId(orderId);
			Trade trade = fmtsRestClient.tradeExecution(order);
			if (trade == null) {
				System.out.println("hello");
				return false;
			}

			else {
				if (verifyTrade(trade)) {
					dao.insertOrder(order);

					trade.getOrder().setOrderId(order.getOrderId());
					trade.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
					dao.insertTrade(trade);
					aggregateInPortfolio(trade);
				} else {

					return false;
				}
				return true;
			}

		} catch (Exception e) {
			throw new Exception("Error while executing trade", e);
		}
	}

	public boolean verifyTrade(Trade trade) {
		Portfolio portfolio;
		if (trade.getDirection().compareTo("S") == 0) {
			portfolio = dao.getInstrumentQuantity(trade.getOrder().getClientId(), trade.getInstrumentId());
			if (portfolio != null) {
				int availableQuantity = portfolio.getQuantity();
				if (availableQuantity >= trade.getQuantity()) {
					return true;
				} else {

					return false;
				}
			} else {

				return false;
			}
		}
		if (trade.getDirection().compareTo("B") == 0) {
			BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
			if (walletAmount.compareTo(trade.getCashValue()) < 0) {
				return false;
			}
			return true;
		}
		return true;
	}

	public void aggregateInPortfolio(Trade trade) throws Exception {
		Portfolio portfolio = null;
		try {
			portfolio = portfolioDao.getPortfolioByClientIdAndInstrumentId(trade.getOrder().getClientId(),
					trade.getInstrumentId());
			if (portfolio == null) {
				portfolio = new Portfolio(trade.getOrder().getClientId(), trade.getInstrumentId(), trade.getQuantity(),
						trade.getCashValue());
				portfolioDao.insertPortfolio(portfolio);
				BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
				walletAmount = walletAmount.subtract(trade.getCashValue());
				clientService.updateClientWallet(trade.getOrder().getClientId(), walletAmount);
			} else {

				if (trade.getDirection().compareTo("B") == 0) {

					BigDecimal originalValue = portfolio.getValue();
					originalValue = originalValue.add(trade.getCashValue());
					if (portfolio.getQuantity() == 0)
						originalValue = trade.getCashValue();
					portfolio.setValue(originalValue);
					portfolio.setQuantity(portfolio.getQuantity() + trade.getQuantity());
					portfolioDao.updatePortfolio(portfolio);
					BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
					walletAmount = walletAmount.subtract(trade.getCashValue());
					clientService.updateClientWallet(trade.getOrder().getClientId(), walletAmount);

				} else {
					BigDecimal originalValue = portfolio.getValue();
					originalValue = originalValue.subtract(trade.getCashValue());
					portfolio.setValue(originalValue);
					portfolio.setQuantity(portfolio.getQuantity() - trade.getQuantity());
					portfolioDao.updatePortfolio(portfolio);
					BigDecimal walletAmount = dao.getWalletAmount(trade.getOrder().getClientId());
					walletAmount = walletAmount.add(trade.getCashValue());
					clientService.updateClientWallet(trade.getOrder().getClientId(), walletAmount);
				}
			}
		} catch (Exception e) {
			throw new Exception("Exception while aggregating portfolio");
		}

	}

	@Override
	public int totalTradesByClientId(String clientId, String q, String _category) throws Exception {
		try {
			q="%"+q+"%";
			_category="%"+_category+"%";
			return dao.totalTradesByClientId(clientId,q,_category);
		}catch(Exception e) {
			throw new Exception("Error while fetching total trades count");
		}
	}

}
