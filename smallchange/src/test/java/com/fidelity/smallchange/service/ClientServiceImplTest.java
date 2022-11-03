package com.fidelity.smallchange.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.integration.mapper.TokenMapper;
import com.fidelity.smallchange.model.Client;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.Country;
import com.fidelity.smallchange.model.Token;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;;

class ClientServiceImplTest {

	@Mock
	FMTSRestClient rc;
	@Mock
	TokenMapper tm;

	@InjectMocks
	ClientService cs;

	@BeforeEach
	void setUp() throws Exception {
		cs = new ClientServiceImpl();
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInsertToken() {
		cs.insertToken(new Client("1234", null, null, null, null, null, "thiastoken"));
		verify(tm, times(1)).insertToken(any());
		;
	}

	@Test
	void testClientVerification() throws HttpClientErrorException, JsonProcessingException {
		when(rc.clientVerification(any())).thenReturn(new Client("1234", null, null, null, null, null, "4321"));
		Client client = cs.clientVerification(
				new ClientDB("1234", null, null, Country.of("US"), null, null, null, null, null, null, null));
		assertThat(client, equalTo(new Client("1234", null, null, null, null, null, "4321")));
	}

	@Test
	void testTokenGenerationWhenOldTokenIsUsed()
			throws HttpClientErrorException, JsonProcessingException, ParseException {
		when(tm.getTokenByClientId(anyString())).thenReturn(
				new Token("1234", "4321", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
		Token token = cs
				.getToken(new ClientDB("1234", null, null, Country.of("US"), null, null, null, null, null, null, null));
		assertThat(token,
				equalTo(new Token("1234", "4321", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))));

		verify(tm, times(1)).updateToken(any());
	}

	@Test
	void testTokenGenerationWhenNewTokenIsUsed()
			throws HttpClientErrorException, JsonProcessingException, ParseException {
		when(rc.clientVerification(any())).thenReturn(new Client("1234", null, null, null, null, null, "4321"));
		when(tm.getTokenByClientId(anyString())).thenReturn(new Token("1234", "4321", "2022-10-27T11:35:00"));
		Token token = cs
				.getToken(new ClientDB("1234", null, null, Country.of("US"), null, null, null, null, null, null, null));
		assertThat(token,
				equalTo(new Token("1234", "4321", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))));

		verify(tm, times(1)).updateToken(any());
	}

}