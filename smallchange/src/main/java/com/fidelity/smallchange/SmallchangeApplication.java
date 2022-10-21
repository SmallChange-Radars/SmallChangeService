package com.fidelity.smallchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fidelity.smallchange.integration.FMTSRestClient;
import com.fidelity.smallchange.model.Client;

@SpringBootApplication
public class SmallchangeApplication implements CommandLineRunner {
	
	@Autowired
    private FMTSRestClient a;
	
	@Override
    public void run(String...args) throws Exception {
        System.out.println(a.clientVerification(new Client("", "dd@gmail.com", null, null, null, null, "")).toString());
    }

	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
