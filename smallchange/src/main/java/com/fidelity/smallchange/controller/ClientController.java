package com.fidelity.smallchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.slf4j.Logger;

import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientPreferences;
import com.fidelity.smallchange.service.ClientService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	private static final String DB_ERROR_MSG = "Error communicating with the Smallchange database";
	
	@Autowired
	private Logger logger;
	
	@Autowired
	private ClientService service;
	
	@GetMapping("/clientInfo")
	public ResponseEntity<ClientDB> getClient(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		logger.debug("Getting clientInfo for client with id = "+ userDetails.getClientId());
		ClientDB client;
		ResponseEntity<ClientDB> result;
		String clientId = userDetails.getClientId();
		try {
			client = service.getClientByClientId(clientId);
		}
		catch(RuntimeException e) {
			logger.error("Exception while getting Client info for the client with id " + clientId);
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if(client == null) {
			result = ResponseEntity.noContent().build();
		}
		else
			result = ResponseEntity.ok(client);
		return result;
	}
	
	@GetMapping("/wallet")
	public ResponseEntity<ClientDB> getClientWallet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		logger.debug("Getting wallet for client with id = "+ userDetails.getClientId());
		ClientDB client;
		ResponseEntity<ClientDB> result;
		String clientId = userDetails.getClientId();
		try {
			client = service.getClientWalletByClientId(clientId);
		}
		catch(RuntimeException e) {
			logger.error("Exception while getting wallet for the client with id " + clientId);
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if(client == null) {
			result = ResponseEntity.noContent().build();
		}
		else
			result = ResponseEntity.ok(client);
		return result;
	}
	
//	@PutMapping(value="/wallet", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
//	public DatabaseRequestResult updateWallet(@RequestBody ClientDB client) {
//		int count = 0;
//		try {
//			count = service.updateClientWallet(client.getClientId(), client.getWallet());
//		}
//		catch (Exception e) {
//			throw new ServerErrorException(DB_ERROR_MSG, e);
//		}
//		return new DatabaseRequestResult(count);
//	}
	
	@PostMapping(value="/preferences", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult insertPreferences(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ClientPreferences clientPref) {
		logger.debug("Inserting Client Preferences for client with id = "+ userDetails.getClientId());
		String clientId = userDetails.getClientId();
		clientPref.setClientId(clientId);
		int count = 0;
		try {
			count = service.insertClientPreferences(clientPref);
		}
		catch (Exception e) {
			logger.error("Exception while inserting preferences for the client with id " + clientId);
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (count == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return new DatabaseRequestResult(count);
	}
	
	@GetMapping("/preferences")
	public ResponseEntity<ClientPreferences> getPreferences(@AuthenticationPrincipal UserDetailsImpl userDetails){
		logger.debug("Getting Client Preferences for client with id = "+ userDetails.getClientId());
		ClientPreferences preferences;
		ResponseEntity<ClientPreferences> result;
		String clientId = userDetails.getClientId();
		try {
			preferences = service.getClientPreferencesById(clientId);
		}
		catch(RuntimeException e) {
			logger.error("Exception while getting preferences for the client with id " + clientId);
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if(preferences == null) {
			result = ResponseEntity.noContent().build();
		}
		else
			result = ResponseEntity.ok(preferences);
		return result;
	}
	
	@PutMapping(value="/preferences", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult updatePreferences(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ClientPreferences preferences) {
		logger.debug("Updating Client Preferences for client with id = "+ userDetails.getClientId());
		int count = 0;
		String clientId = userDetails.getClientId();
		preferences.setClientId(clientId);
		try {
			count = service.updateClientPreferences(preferences);
		}
		catch (Exception e) {
			logger.error("Exception while updating preferences for the client with id " + clientId);
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		return new DatabaseRequestResult(count);
	}
}
