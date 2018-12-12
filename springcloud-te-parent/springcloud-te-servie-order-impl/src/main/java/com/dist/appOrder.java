package com.dist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;

@SpringBootApplication
@EnableFeignClients //feign调用必备
@EnableEurekaClient  //注册(消费)必备
@EnableHystrix   //开启服务保护机制
@EnableSwagger2Doc
public class appOrder {

	public static void main(String[] args) {
		SpringApplication.run(appOrder.class, args);
	}
}
