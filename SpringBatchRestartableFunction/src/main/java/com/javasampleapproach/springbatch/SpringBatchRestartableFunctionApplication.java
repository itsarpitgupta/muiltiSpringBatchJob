package com.javasampleapproach.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchRestartableFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchRestartableFunctionApplication.class, args);
	}
}
