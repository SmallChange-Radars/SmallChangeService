package com.fidelity.smallchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.fidelity.smallchange.model.ClientPreferences;
import com.fidelity.smallchange.service.ClientPreferencesService;
import com.fidelity.smallchange.service.UserDetailsImpl;

@RestController
@RequestMapping("/client/Preferences")
public class ClientPreferencesController {
	
	@Autowired
	private ClientPreferencesService service;
	
	@PostMapping("/insert")
	public int insertClientPreferences(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ClientPreferences clientPreferences) {
		return service.insertClientPreferences(clientPreferences, userDetails.getClientId());
	}
	
	@PutMapping("/update")
	public int updateClientPreferences(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ClientPreferences clientPreferences) {
		return service.updateClientPreferences(clientPreferences, userDetails.getClientId());
	}
	
	@GetMapping()
	public ResponseEntity<ClientPreferences> getClientPreferences(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		System.out.println(userDetails.getClientId());
		try {
			ClientPreferences preference  = service.getClientPreferencesById(userDetails.getClientId());
			ResponseEntity<ClientPreferences> responseEntity;
			if(preference!=null) {
				responseEntity = ResponseEntity.ok(preference);
			}
			else {
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		}
		catch(Exception e) {
			throw new ServerErrorException("Error getting client preferences",e);
		}
		
	}

}
