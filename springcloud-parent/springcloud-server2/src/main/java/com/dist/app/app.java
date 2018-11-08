package com.dist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * 初次集群的时候可能会报错，因为两台是相互注册的，
 * 所以必定会有一台在最初的时候无法注册
 * 到绑定的地址，但是后续的重试服务可以解决这个问题
 * 
 * 2.注意在注册服务启动后只会在一个端口上注册服务，相当于主服务
 * 如果主服务宕机了，eureka会自动的把主服务上的东西过度到从服务器上面
 * 此过程大概需要30S(系统默认)
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class app {

	
	public static void main(String[] args) {
		SpringApplication.run(app.class, args);
	}
}
