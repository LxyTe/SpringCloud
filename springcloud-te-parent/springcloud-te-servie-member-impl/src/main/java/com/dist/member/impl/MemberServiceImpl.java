package com.dist.member.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dist.api.service.IMemberService;
import com.dist.entity.UserEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@RestController
@Api("会员服务swagger")
public class MemberServiceImpl implements IMemberService {

	
	@ApiOperation(value="得到某个会员信息")
	@RequestMapping(value="/getMember" ,method=RequestMethod.GET)
	public UserEntity getMember() {
		// TODO Auto-generated method stub
		UserEntity userEntity =new UserEntity();
		userEntity.setName("Te");
		userEntity.setPas("123");
		return userEntity;
	}

	@Override
	public String getUserInfo() {
		// TODO Auto-generated method stub
		try {			
			Thread.sleep(1500);//1.5秒延迟
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "true,延迟之后调用成功 ";
	}

}
