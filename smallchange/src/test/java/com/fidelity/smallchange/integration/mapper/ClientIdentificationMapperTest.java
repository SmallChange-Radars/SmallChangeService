package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.ClientIdentification;

@SpringBootTest
class ClientIdentificationMapperTest {
	
	@Autowired
	ClientIdentificationMapper dao;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetClientIdentificationByClientId() {
		ClientIdentification obj = new ClientIdentification("SSN", "SSNVal1");
		List<ClientIdentification> cIList = new ArrayList<>();
		cIList.add(obj);
		assertEquals(cIList, dao.getClientIdentificationByClientId("1234"));
	}
//	
//	@Test
//	@Transactional
//	void testInsertClientIdentification() {
//		ClientIdentification obj = new ClientIdentification("SSN", "ThisIsAVal");
//		dao.insertClientIdentification(obj, "1235");
//		List<ClientIdentification> cIList = new ArrayList<>();
//		cIList.add(obj);
//		assertEquals(cIList, dao.getClientIdentificationByClientId("1235"));
//}

}
