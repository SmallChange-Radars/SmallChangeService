package com.fidelity.smallchange.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.fidelity.smallchange.model.ClientIdentification;

@Mapper
public interface ClientIdentificationMapper {

	@Select("""
			SELECT type, value
			FROM ClientIdentification
			WHERE clientId = #{clientId}
			""")
	List<ClientIdentification> getClientIdentificationByClient(String clientId);
}
