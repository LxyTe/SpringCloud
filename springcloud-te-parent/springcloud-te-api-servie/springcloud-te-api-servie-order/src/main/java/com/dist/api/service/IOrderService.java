package com.dist.api.service;

import org.springframework.web.bind.annotation.RequestMapping;

public interface IOrderService {

	//feign客户端调用服务
	@RequestMapping("/orderToMember")
	public String orderToMember();
	
}
