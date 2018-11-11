package com.dist.api.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.dist.entity.UserEntity;

/**
 * 服务接口类
 * @author Administrator
 *
 */

public interface IMemberService {

	@RequestMapping("/getMember")
	public UserEntity getMember( );
}
