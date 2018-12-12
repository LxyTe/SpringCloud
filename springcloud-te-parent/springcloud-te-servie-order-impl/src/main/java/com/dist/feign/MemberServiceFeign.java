package com.dist.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.dist.api.service.IMemberService;
import com.dist.feign.fallback.MemberServiceFallback;
/**
 * 定义一个类专门来实现服务降级处理
 * @author Administrator
 *
 */
@FeignClient(value = "member" , fallback=MemberServiceFallback.class)  //服务的提供者在注册中心中的名字,实现的接口是服务的提供者基层接口
public interface MemberServiceFeign  extends IMemberService{

}
