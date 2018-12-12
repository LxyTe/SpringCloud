package com.dist.order;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dist.api.service.IOrderService;
import com.dist.entity.UserEntity;
import com.dist.feign.MemberServiceFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api("订单服务swagger")
public class orderServiceImpl implements IOrderService{

	@Autowired
	private MemberServiceFeign memberServiceFeign;
	@ApiOperation("调用会员服务得到会员信息")
    @RequestMapping(value="/orderToMember",method=RequestMethod.GET)
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
    
    @HystrixCommand(fallbackMethod="tt")
    @ApiOperation(value="验证HystrixCommand的swagger描述")
    @RequestMapping(value="/orderToMemberUserInfo" , method= RequestMethod.GET)
	public String orderToMemberUserInfo() {		
	String result =	memberServiceFeign.getUserInfo();	
		return result;
	}

     public String tt(){
    	 return "由于当前服务器繁忙，请稍后再试";
     }
}
