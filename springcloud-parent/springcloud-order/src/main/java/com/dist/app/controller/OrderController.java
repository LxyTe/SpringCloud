package com.dist.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

	//RestTemplate 是有springboot web组件提供 默认整合 ribbon负载均衡器的
	//rest方式底层是采用 httpclient技术
	 @Autowired
	private RestTemplate restTemplate;

	 @RequestMapping("/getOrder")
    public String getOrder(){
		 //不走注册中心，直连调用
		// restTemplate.getForObject("http://127.0.0.1:1617/MM", String.class)
    
		 /**使用别名去注册中心获取服务
		  * ---如果启动的时候报错找不懂映射的服务，那说明缺少 负载均衡器
		  * 
		  * springcloud可以用feign 和rest模板两种方式实现远程调用
		  * */
		 String url="http://hi/MM"; 
		 return  restTemplate.getForObject(url, String.class);
    }
}
