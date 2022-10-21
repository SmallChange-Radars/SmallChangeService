package com.fidelity.smallchange;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fidelity.smallchange.integration.mapper.ClientIdentificationMapper;
import com.fidelity.smallchange.integration.mapper.ClientMapper;
import com.fidelity.smallchange.integration.mapper.PortfolioMapper;
import com.fidelity.smallchange.model.ClientDB;
import com.fidelity.smallchange.model.ClientIdentification;
import com.fidelity.smallchange.model.Portfolio;

@SpringBootApplication
public class SmallchangeApplication implements CommandLineRunner {

	
	@Autowired
	private ClientMapper client;
	
	@Autowired
	private ClientIdentificationMapper clienti;
	
	@Override
    public void run(String...args) throws Exception {
        List<ClientDB> c = client.getAllClients();
        System.out.println(c.toString());
        List<ClientIdentification> ci = clienti.getClientIdentificationByClient("1234");
        System.out.println(ci.toString());
    }

	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
