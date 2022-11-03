package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.Country;

@SpringBootTest
class ClientMapperTest {
	
	@Autowired
	private ClientMapper dao;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllClients() {
		List<ClientDB> p = dao.getAllClients();
		assertEquals(5, p.size());
	}
	
	@Test
	void testGetClientByClientId() {
		ClientDB c = dao.getClientByClientId("1235");
		List <ClientIdentification> ciList = new ArrayList<>();
		//assertEquals(new ClientDB("1235", "sara@gmail.com", "20002512", Country.US, "123477", ciList,  new BigDecimal("748295.45"), "USD"), c);
	}
	
	@Test
	@Transactional
	void testInsertClient() {
		//ClientDB c = new ClientDB("1240", "bruh@gmail.com", "20001909", Country.IN, "123487", new ArrayList<ClientIdentification>(), new BigDecimal("1234.89"), "USD");
		//dao.insertClient(c);
		//assertEquals(c, dao.getClientByClientId("1240"));
	}
	
	@Test
	@Transactional
	void testUpdateClient() {
		//ClientDB c = new ClientDB("1235", "bruh@gmail.com", "20001909", Country.IN, "123487", new ArrayList<ClientIdentification>(), new BigDecimal("1234.89"), "USD");
		//assertNotEquals(c, dao.getClientByClientId("1235"));
		//dao.updateClient(c);
		//assertEquals(c, dao.getClientByClientId("1235"));
	}
	
	
}
