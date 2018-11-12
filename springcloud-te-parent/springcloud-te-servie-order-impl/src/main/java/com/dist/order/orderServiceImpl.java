package com.dist.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dist.api.service.IOrderService;
import com.dist.entity.UserEntity;
import com.dist.feign.MemberServiceFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
public class orderServiceImpl implements IOrderService{

	@Autowired
	private MemberServiceFeign memberServiceFeign;
    @RequestMapping("/orderToMember")
	public String orderToMember() {
		 UserEntity yEntity= memberServiceFeign.getMember();
		return yEntity.toString();
	}
	
    /**
     * @HystrixCommand 
     *    
     *     默认开启 线程池隔离模式，服务降级，服务熔断
     *     fallbackMethod 服务熔断，降级之后调用的统一方法tt
     */
    
    @HystrixCommand(fallbackMethod = "tt")
    @RequestMapping("/orderToMemberUserInfo")
	public String orderToMemberUserInfo() {
		
	String result =	memberServiceFeign.getUserInfo();
		return result;
	}

     public String tt(){
    	 return "由于当前服务器繁忙，请稍后再试";
     }
}
