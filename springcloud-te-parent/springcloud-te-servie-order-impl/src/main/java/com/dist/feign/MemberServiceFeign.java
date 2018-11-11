package com.dist.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.dist.api.service.IMemberService;

@FeignClient("member")  //服务的提供者在注册中心中的名字,实现的接口是服务的提供者基层接口
public interface MemberServiceFeign  extends IMemberService{

}
