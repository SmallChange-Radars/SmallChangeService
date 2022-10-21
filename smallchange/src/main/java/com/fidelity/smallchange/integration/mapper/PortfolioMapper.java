package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.fidelity.smallchange.model.Portfolio;

@Mapper
public interface PortfolioMapper {
	
	@Select("""
			SELECT clientId, instrumentId, quantity, value
			FROM portfolio
			""")
	public List<Portfolio> getAllPortfolios();
	
	@Select("""
			SELECT clientId, instrumentId, quantity, value
			FROM portfolio
			WHERE clientId = #{clientId}
			""")
	public List<Portfolio> getPortfolioByClientId(String clientId);
	
	@Insert("""
			INSERT 
			INTO portfolio (clientId, instrumentId, quantity, value)
			VALUES (#{clientId}, #{instrumentId}, #{quantity}, #{value})
			""")
	public void insertPortfolio(Portfolio portfolio);
	
	@Delete("""
			DELETE 
			FROM portfolio
			WHERE instrumentId = #{instrumentId}
			AND clientId = #{clientId}
			""")
	public void deletePortfolioByClientIdAndInstrumentId(String clientId, String instrumentId);
}
