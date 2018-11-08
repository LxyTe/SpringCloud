package com.dist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient //注册或消费
public class app {

	public static void main(String[] args) {
		SpringApplication.run(app.class, args);
	}
	
	//解决RestTemplate模板没有注册bean的问题 @bean
  @Bean
  @LoadBalanced //自带的负载均衡/..... 默认rest方式启动的时候会检查负载均衡如果没有此注解，会找不到在注册中心里面的别名
  RestTemplate restTemplate(){
	  return new RestTemplate();
  }
}
