package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fidelity.smallchange.model.ClientDB;

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
	void testGetAllPortfolios() {
		List<ClientDB> p = dao.getAllClients();
		assertEquals(6, p.size());
	}
}
