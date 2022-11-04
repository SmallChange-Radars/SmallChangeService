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
	List<ClientIdentification> getClientIdentificationByClientId(String clientId);
	
	@Insert("""
			INSERT into ClientIdentification
			(type, value, clientId)
			VALUES(#{type}, #{value}, #{clientId})
			""")
	int insertClientIdentification(String type, String value, String clientId);
	
	@Update("""
			UPDATE ClientIdentification
			SET type = #{type}, value = #{value}
			WHERE clientId = #{clientId}
			""")
	int updateClientIdentification(String type, String value, String clientId);
}
