package com.dist.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dist.api.service.IOrderService;
import com.dist.entity.UserEntity;
import com.dist.feign.MemberServiceFeign;


@RestController
public class orderServiceImpl implements IOrderService{

	@Autowired
	private MemberServiceFeign memberServiceFeign;
    @RequestMapping("/orderToMember")
	public String orderToMember() {
		 UserEntity yEntity= memberServiceFeign.getMember();
		return yEntity.toString();
	}

}
