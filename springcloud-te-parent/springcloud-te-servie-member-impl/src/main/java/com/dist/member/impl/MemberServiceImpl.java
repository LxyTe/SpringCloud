package com.dist.member.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dist.api.service.IMemberService;
import com.dist.entity.UserEntity;



@RestController
public class MemberServiceImpl implements IMemberService {

	
	//@RequestMapping("/getMember")
	public UserEntity getMember() {
		// TODO Auto-generated method stub
		UserEntity userEntity =new UserEntity();
		userEntity.setName("Te");
		userEntity.setPas("123");
		return userEntity;
	}

}
