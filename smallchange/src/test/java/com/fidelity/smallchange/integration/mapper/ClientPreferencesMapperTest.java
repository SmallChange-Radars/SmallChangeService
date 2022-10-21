package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.ClientPreferences;


@SpringBootTest
class ClientPreferencesMapperTest {
	
	@Autowired
	ClientPreferencesMapper dao;
	
	@Test
	void testGetClientPreferences() {
		ClientPreferences cp = dao.getClientPreferences("1238");
		assertEquals(new ClientPreferences("1238", "Savings", 2,4,2), cp);
	}
	
	
	@Test 
	@Transactional
	void testInsertClientPreferences() { 
		ClientPreferences newCp = new ClientPreferences("1234", "Test", 1, 1, 1);
		dao.insertClientPreferences(newCp);
		assertEquals(newCp, dao.getClientPreferences("1234"));
	}
  
	@Test 
	@Transactional
	void testUpdateClientPreferences() { 
		ClientPreferences updatedCp1238 = new ClientPreferences("1238", "Test", 1, 1, 1);
		assertNotEquals(updatedCp1238, dao.getClientPreferences("1238"));
		dao.updateClientPreferences(updatedCp1238);
		assertEquals(updatedCp1238, dao.getClientPreferences("1238"));
	}
 

}
