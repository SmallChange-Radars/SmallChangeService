package com.fidelity.smallchange.integration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fidelity.smallchange.model.Token;

@Mapper
public interface TokenMapper {

	@Select("""
			SELECT clientId,token, timestamp
			FROM fmtsToken
			WHERE clientId = #{clientId}
			""")
	public Token getTokenByClientId(String clientId);
	
	@Insert("""
			Insert INTO fmtsToken
			(clientId,token, timestamp)
			VALUES 
			(#{clientId}, #{token}, #{timestamp})
			""")
	public void insertToken(Token client);
	
	@Update("""
			UPDATE fmtsToken
			SET token = #{token}, timestamp = #{timestamp}
			where clientId = #{clientId}
			""")
	public void updateToken(Token client);

}
