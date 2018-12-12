package com.dist.feign.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dist.entity.UserEntity;
import com.dist.feign.MemberServiceFeign;

@Component
public class MemberServiceFallback  implements MemberServiceFeign{

	@Override
	public UserEntity getMember() {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping("/getUserInfo")
	public String getUserInfo() {
		// TODO Auto-generated method stub
		return "服务器忙，请稍后重试";
	}

}
