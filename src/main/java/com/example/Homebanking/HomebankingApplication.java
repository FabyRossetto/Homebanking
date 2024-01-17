package com.example.Homebanking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Homebanking")
public class HomebankingApplication {

        
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

}
