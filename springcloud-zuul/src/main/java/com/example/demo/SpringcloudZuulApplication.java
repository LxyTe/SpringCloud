package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.example.demo.filter.TokenFilter;
import com.spring4all.swagger.EnableSwagger2Doc;

@SpringBootApplication
@EnableZuulProxy   //开启网关
@EnableEurekaClient   //注册服务到eureka中
@EnableSwagger2Doc
public class SpringcloudZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudZuulApplication.class, args);
	}
	
	@Bean
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}
	//添加文档来源
	
}
