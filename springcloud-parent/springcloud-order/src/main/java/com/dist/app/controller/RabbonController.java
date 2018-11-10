package com.dist.app.controller;

import java.util.List;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.converters.Auto;

/**
 * eureka负载均衡原理
 * @author Administrator
 *
 */

@RestController
public class RabbonController {

	//可以获取注册 中心的服务对象
	@Autowired
	private DiscoveryClient discoveryClient;
  
	@Autowired
	private RestTemplate restTemplate;
	//接口请求总数
	private int reqCount;
   public String ribbonMember(){
	   //1.获取对应服务器远程调用地址
	     
	 String URL=   getItance()+"/MM";//要调用的接口名字

	 System.out.println("准备调用的接口:"+URL);
	  //2.使用rest方式发送请求
	String result=  restTemplate.getForObject(URL, String.class);
	   return result;
   }
   
   private String getItance(){
	   List<ServiceInstance> data=   discoveryClient.getInstances("Hi");
	   
		  if(data ==null || data.size()<=0){
			  return "";
		  }else{
			  
		  }
		 int size=data.size();//服务总数
		 int serviceIndex= reqCount % size;
	     reqCount ++;//此线程是不安全的
	     //返回调用后的信息
	   return data.get(serviceIndex).getUri().toString();
   }
}
