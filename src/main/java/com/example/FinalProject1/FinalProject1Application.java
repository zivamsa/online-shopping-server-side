package com.example.FinalProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.example.FinalProject1.controllers","com.example.FinalProject1.services"})
@EntityScan("com.example.FinalProject1.models")
@EnableJpaRepositories("com.example.FinalProject1.repo")
@SpringBootApplication
public class FinalProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(FinalProject1Application.class, args);
	}

}
