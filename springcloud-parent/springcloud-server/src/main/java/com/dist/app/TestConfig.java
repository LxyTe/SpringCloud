package com.dist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaServer
@ComponentScan("com.dist.*")
public class TestConfig {

	
	public static void main(String[] args) {
	    SpringApplication.run(TestConfig.class, args);
	}
}
