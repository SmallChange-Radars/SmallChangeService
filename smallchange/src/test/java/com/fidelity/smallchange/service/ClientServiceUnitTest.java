package com.fidelity.smallchange.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.ClientDao;
import com.fidelity.smallchange.integration.ClientIdentificationDao;
import com.fidelity.smallchange.integration.ClientPreferencesDao;
import com.fidelity.smallchange.integration.DatabaseException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.mapper.TokenMapper;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.ClientPreferences;
import com.fidelity.smallchange.model.Country;
import com.fidelity.smallchange.model.Token;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Transactional
class ClientServiceUnitTest {

	@Mock
	FMTSRestClient rc;

	@Mock
	TokenMapper tm;

	@Mock
	ClientDao dao;
	
	@Mock
	ClientPreferencesDao pDao;
	
	@Mock
	ClientIdentificationDao iDao;

	@InjectMocks
	ClientService cs;
	
//	@InjectMocks
//	PasswordEncoder encoder;

	@BeforeEach
	void setUp() throws Exception {
		cs = new ClientServiceImpl();
//		encoder = new BCryptPasswordEncoder();
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testClientVerification() throws HttpClientErrorException, JsonProcessingException {
		when(rc.clientVerification(any())).thenReturn(new Client("290988199", null, null, null, null, null, "4321"));
		Client client = cs.clientVerification(
				new ClientDB("290988199", null, null, Country.of("US"), null, null, null, null, null, null, null));
		assertThat(client, equalTo(new Client("290988199", null, null, null, null, null, "4321")));
	}

	@Test
	void testTokenGenerationWhenOldTokenIsUsed()
			throws HttpClientErrorException, JsonProcessingException, ParseException {
		when(tm.getTokenByClientId(anyString())).thenReturn(
				new Token("290988199", "4321", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
		Token token = cs.getToken("290988199");
		assertThat(token, equalTo(
				new Token("290988199", "4321", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))));

		verify(tm, times(1)).updateToken(any());
	}

	@Test
	void testTokenGenerationWhenNewTokenIsUsed()
			throws HttpClientErrorException, JsonProcessingException, ParseException {
		when(rc.clientVerification(any())).thenReturn(new Client("290988199", null, null, null, null, null, "4321"));
		when(tm.getTokenByClientId(anyString())).thenReturn(new Token("290988199", "4321", "2022-10-27T11:35:00"));
		when(tm.getTokenByClientId(anyString())).thenReturn(new Token("290988199", "4321", "2022-10-27T11:35:00"));

		when(dao.getClientById(any())).thenReturn(
				new ClientDB("290988199", null, null, Country.of("US"), null, null, null, null, null, null, null));
		Token token = cs.getToken("290988199");
		assertThat(token, equalTo(
				new Token("290988199", "4321", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))));

		verify(tm, times(1)).updateToken(any());
	}
	
	@Test
	void testGetClientByClientId() {
		String clientId = "1234";
		ClientDB client = new ClientDB("1234", "aadrs@gmail.com", "19900101", Country.US, "123456", new ArrayList<ClientIdentification>(), null, null, new BigDecimal("12345.45"), "USD", null);
		when(dao.getClientById(clientId)).thenReturn(client);
		
		ClientDB result = cs.getClientByClientId(clientId);
		assertThat(result, is(equalTo(client)));
	}
	
	@Test
	void testGetClientByClientId_ThrowsException() {
		String clientId = "9999";
		
		when(dao.getClientById(clientId)).thenThrow(DatabaseException.class);
		assertThrows(DatabaseException.class, () -> cs.getClientByClientId(clientId));
	}
	
//	@Test
//	void testInsertClient() {
//		String clientId = "10489";
//		ClientDB client = new ClientDB(clientId, "aadrs@gmail.com", "19900101", Country.US, "123456", new ArrayList<ClientIdentification>(), null, "a", new BigDecimal("12345.45"), "USD", null);
//		Client c = new Client();
//		c.setClientId(clientId);
//		int expected = 1;
//		
//		when(dao.insertClient(client)).thenReturn(1);
//		int count = cs.insertClient(client, c);
//		
//		assertThat(count, equalTo(expected));	
//	}
	
	@Test
	void testCheckClientByEmail() {
		String email = "aadrs@gmail.com";
		when(dao.checkClientByEmail(email)).thenReturn(1);
		
		boolean result = cs.checkClientByEmail(email);
		
		assertThat(result, equalTo(true));
	}
	
	@Test
	void testCheckClientByEmail_NoResult() {
		String email = "abc@gmail.com";
		when(dao.checkClientByEmail(email)).thenReturn(0);
		
		boolean result = cs.checkClientByEmail(email);
		
		assertThat(result, equalTo(false));
	}
	
	@Test
	void testCheckClientByEmail_ThrowsException() {
		String email = "aadrs@gmail.com";
		when(dao.checkClientByEmail(email)).thenThrow(DatabaseException.class);
		
		assertThrows(DatabaseException.class, () -> {cs.checkClientByEmail(email);});
	}
	
	@Test
	void testGetClientWalletById() {
		String clientId = "1234";
		ClientDB client = new ClientDB(null, null, null, null, null, null, null, null, new BigDecimal("12345.45"), "USD", null);

		when(dao.getClientWalletById(clientId)).thenReturn(client);
		ClientDB result = cs.getClientWalletByClientId(clientId);
		assertThat(result, is(equalTo(client)));
	}
	
	@Test
	void testGetClientWalletById_ThrowsException() {
		String clientId = "1234";
		when(dao.getClientWalletById(clientId)).thenThrow(DatabaseException.class);
		assertThrows(DatabaseException.class, () -> {cs.getClientWalletByClientId(clientId);});
	}
	
	@Test
	void testUpdateClientWallet() {
		String clientId = "1234";
		int expected = 1;
		
		when(dao.updateClientWallet(new BigDecimal("10.00"), clientId)).thenReturn(1);
		
		int count = cs.updateClientWallet(clientId, new BigDecimal("10.00"));

		assertThat(count, equalTo(expected));
	}
	
	@Test
	void testUpdateClientWallet_ThrowsException() {
		String clientId = "1234";
		when(dao.updateClientWallet(new BigDecimal("10.00"), clientId)).thenThrow(DatabaseException.class);
		
		assertThrows(DatabaseException.class, () -> {cs.updateClientWallet(clientId, new BigDecimal("10.00"));});
	}
	
	@Test
	void testGetClientPreferencesById() {
		String clientId = "1235";
		ClientPreferences preferences = new ClientPreferences(clientId, "Savings", 5, 3, 4);
		when(pDao.getClientPreferences(clientId)).thenReturn(preferences);
		
		ClientPreferences result = cs.getClientPreferencesById(clientId);
		assertThat(result, is(equalTo(preferences)));
	}
	
	@Test
	void testGetClientPreferencesById_ThrowsException() {
		String clientId = "1235";
		when(pDao.getClientPreferences(clientId)).thenThrow(DatabaseException.class);
		
		assertThrows(DatabaseException.class, () -> {cs.getClientPreferencesById(clientId);});
	}
	
	@Test
	void testInsertClientPreferences() {
		String clientId = "9999";
		ClientPreferences preferences = new ClientPreferences(clientId, "Savings", 5, 3, 4);
		int expected = 1;		
		when(pDao.insertClientPreferences(preferences)).thenReturn(1);
		int count = cs.insertClientPreferences(preferences);
		
		assertThat(count, equalTo(expected));
	}
	
	@Test
	void testInsertClientPreferences_ThrowsException() {
		String clientId = "1235";
		ClientPreferences preferences = new ClientPreferences(clientId, "Savings", 5, 3, 4);
		when(pDao.insertClientPreferences(preferences)).thenThrow(DatabaseException.class);
		
		assertThrows(DatabaseException.class, () -> {cs.insertClientPreferences(preferences);});
	}
	
	@Test
	void testUpdateClientPreferences() {
		ClientPreferences preferences = new ClientPreferences("1235", "Savings", 5, 3, 4);
		when(pDao.updateClientPreferences(preferences)).thenReturn(1);
		
		int count = cs.updateClientPreferences(preferences);
		assertThat(count, equalTo(1));
	}
	
	@Test
	void testUpdateClientPreferences_ThrowsException() {
		String clientId = "1235";
		ClientPreferences preferences = new ClientPreferences(clientId, "Savings", 5, 3, 4);
		when(pDao.updateClientPreferences(preferences)).thenThrow(DatabaseException.class);
		
		assertThrows(DatabaseException.class, () -> {cs.updateClientPreferences(preferences);});
	}

}
