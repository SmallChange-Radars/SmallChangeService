package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;

import com.fidelity.smallchange.model.ClientDB;

@Mapper
public interface ClientMapper {
	
	@Select("""
			SELECT clientId, email, dob as dateOfBirth, country, postalCode, wallet, walletCurrency
			FROM client 
			ORDER BY clientId
			""")
	@Results({
		@Result(property="clientId", column="clientId", id=true),
		@Result(property="clientIdentification", column="clientId",
				many=@Many(select="com.fidelity.smallchange.integration.mapper.ClientIdentificationMapper.getClientIdentificationByClientId"))
	})
	public List<ClientDB> getAllClients();
	
	@Select("""
			SELECT clientId, email, dob as dateOfBirth, country, postalCode, wallet, walletCurrency
			FROM client
			WHERE clientId=${clientId}
			""")
	@Results({
		@Result(property="clientId", column="clientId", id=true),
		@Result(property="clientIdentification", column="clientId",
				many=@Many(select="com.fidelity.smallchange.integration.mapper.ClientIdentificationMapper.getClientIdentificationByClientId"))
	})
	public ClientDB getClientByClientId(String clientId);
	
	@Select("""
			SELECT clientId, email, dob as dateOfBirth, country, postalCode, wallet, walletCurrency,role,password
			FROM client
			WHERE email like #{email}
			""")
	public ClientDB getClientByEmail(String email);
	
	@Insert("""
			INSERT into client
			(clientId, email, dob, country, postalCode, wallet, walletCurrency, role,password)
			VALUES(#{clientId}, #{email}, #{dateOfBirth}, #{country}, #{postalCode}, #{wallet}, #{walletCurrency}, #{role},#{password})
			""")
	public int insertClient(ClientDB client);
	
	@Update("""
			UPDATE client
			SET email = #{email}, dob = #{dateOfBirth}, country = #{country},
			postalCode = #{postalCode}, wallet = #{wallet}, walletCurrency = #{walletCurrency}, role=#{role}
			WHERE clientId = #{clientId}
			""")
	public void updateClient(ClientDB client);
	
}
