package com.dist.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient //当不使用eureka做注册中心需要使用这个注解（zookeeper和consul）
@RestController //测试可以这样写，但不推荐
public class app {

	@Value("${server.port}")
	private String port;
	
	public String getPort(){
		return "端口号:"+port;
	} 
	
	public static void main(String[] args) {
		SpringApplication.run(app.class, args);
	}
}
