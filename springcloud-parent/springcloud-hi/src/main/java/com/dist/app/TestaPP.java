package com.dist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //注册服务或消费
public class TestaPP {

	public static void main(String[] args) {
		SpringApplication.run(TestaPP.class, args);
	}
}
