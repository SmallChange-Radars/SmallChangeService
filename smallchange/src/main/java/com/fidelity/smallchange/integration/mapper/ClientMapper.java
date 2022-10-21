package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
				many=@Many(select="com.fidelity.smallchange.integration.mapper.ClientIdentificationMapper.getClientIdentificationByClient"))
	})
	public List<ClientDB> getAllClients();
}
