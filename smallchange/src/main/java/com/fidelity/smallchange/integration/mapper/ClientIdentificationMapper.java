package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fidelity.smallchange.model.ClientIdentification;

@Mapper
public interface ClientIdentificationMapper {

	@Select("""
			SELECT type, value
			FROM ClientIdentification
			WHERE clientId = ${clientId}
			""")
	public List<ClientIdentification> getClientIdentificationByClientId(String clientId);
	
	@Insert("""
			INSERT into ClientIdentification
			(clientId, type, value)
			VALUES(#{clientId}, ${type}, ${value})
			""")
	public void insertClientIdentification(ClientIdentification clientIdentification, String clientId);
	
	@Update("""
			UPDATE ClientIdentification
			SET type = #{type}, value = #{value}
			WHERE clientId = ${clientId}
			""")
	void updateClientIdentification(ClientIdentification clientIdentification, String clientId);
}
