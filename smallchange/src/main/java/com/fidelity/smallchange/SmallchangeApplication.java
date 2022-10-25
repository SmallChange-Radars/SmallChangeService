package com.fidelity.smallchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmallchangeApplication implements CommandLineRunner {

	@Override
    public void run(String...args) throws Exception {
		
	}

	public static void main(String[] args) {
		SpringApplication.run(SmallchangeApplication.class, args);
	}

}
