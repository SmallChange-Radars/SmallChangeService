package com.fidelity.smallchange.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import com.fidelity.smallchange.model.Client;

@SpringBootTest
class FMTSRestClientTest {
	
	@Autowired
	private FMTSRestClient a;
	
	Client client1,client2;
	

	@BeforeEach
	void setUp() throws Exception {
		client1=new Client("","dd@gmail.com", null, null, null, null, "");
		
		//Empty Email is incorrect details
		client2=new Client("","", null, null, null, null, "");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void GetClientVerifiedWithCorrectCredentialstest() {
		Client client=a.clientVerification(client1);
		assertEquals(client.getEmail(),client1.getEmail());
		
		//update client1 clientid to the one returned from db
		client1.setClientId(client2.getClientId());
	}
	
	@Test
	void Get406WithIncorrectCredentialstest() {
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			a.clientVerification(client2);
		});
		assertEquals("406 Not Acceptable: \"n/a\"", ex.getMessage(), "Should return this error msg when invalid email");
	}

}
