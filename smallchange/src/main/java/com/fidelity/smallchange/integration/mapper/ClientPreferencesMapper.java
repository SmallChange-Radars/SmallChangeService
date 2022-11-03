package com.fidelity.smallchange.integration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fidelity.smallchange.model.ClientPreferences;

@Mapper
public interface ClientPreferencesMapper {
	
	@Select("""
			SELECT clientId, investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment 
			FROM preferences
			WHERE clientId = #{clientId}
			""")
	ClientPreferences getClientPreferences(String clientId);
	
	@Insert("""
			INSERT
			INTO preferences
			(clientId,investmentPurpose, riskTolerance, incomeCategory,lengthOfInvestment)
			VALUES (#{clientId}, #{investmentPurpose}, #{riskTolerance}, #{incomeCategory}, #{lengthOfInvestment})
			""")
	int insertClientPreferences(ClientPreferences clientPreferences);
	
	@Update("""
			UPDATE preferences
			SET investmentPurpose = #{investmentPurpose}, riskTolerance = #{riskTolerance},
			incomeCategory = #{incomeCategory},lengthOfInvestment = #{lengthOfInvestment}
			WHERE clientId = #{clientId}
			""")
	int updateClientPreferences(ClientPreferences clientPreferences);
}
