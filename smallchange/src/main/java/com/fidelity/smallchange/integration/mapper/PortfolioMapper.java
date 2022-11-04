package com.fidelity.smallchange.integration.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	@Select("""
			SELECT clientId, instrumentId, quantity, value
			FROM portfolio
            WHERE clientId = #{clientId} AND instrumentId = #{instrumentId}
			""")
	public void getPortfolioByClientIdAndInstrumentId(String clientId, String instrumentId);
	
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
	
	@Update("""
			UPDATE portfolio
			SET quantity = #{quantity}, value = #{value}
			WHERE instrumentId = #{instrumentId}
			AND clientId = #{clientId}
			""")
	public void updatePortfolio(String clientId, String instrumentId, int quantity, BigDecimal value);
}
