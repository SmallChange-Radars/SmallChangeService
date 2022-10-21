package com.fidelity.smallchange.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelity.smallchange.model.Client;

@Repository("FMTSClientRestImpl")
public class FMTSRestClient  {
	
	String url = "http://localhost:3000/fmts";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    ObjectMapper objectMapper;

	public Client clientVerification(Client client) {
		Client verified=new Client();
		
		HttpHeaders headers = new HttpHeaders();   
	    headers.set("Content-Type", "application/json");      
	 
	    HttpEntity<Client> request = new HttpEntity<>(client, headers);
	    String response = restTemplate.postForObject(url+"/client", request, String.class);
	    
	    try {
			verified = objectMapper.readValue(response, Client.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return verified;
	}

}
