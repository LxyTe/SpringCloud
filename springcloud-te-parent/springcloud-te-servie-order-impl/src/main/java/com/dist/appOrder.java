package com.dist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //feign调用必备
@EnableEurekaClient  //注册(消费)必备
public class appOrder {

	public static void main(String[] args) {
		SpringApplication.run(appOrder.class, args);
	}
}
