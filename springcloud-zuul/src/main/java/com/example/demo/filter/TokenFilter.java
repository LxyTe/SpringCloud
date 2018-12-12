package com.example.demo.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * 网关过滤器
 * @author Administrator
 *
 */
public class TokenFilter extends ZuulFilter {

	@Value("${server.port}")
	private  String serverPort;
	@Override//编写过滤器拦截业务逻辑代码
	public Object run() throws ZuulException {
		// 拦截所有服务接口，判断服务接口是传有userToken参数
		//1.获取上下文
		RequestContext currentContext=RequestContext.getCurrentContext();
		//2.获取request
		HttpServletRequest request =currentContext.getRequest();
		//3.获取token的时候，从请求头中获取
	String userToken =	request.getParameter("userToken");
	  if(StringUtils.isEmpty(userToken)){
		//不会继续执行，  调用服务接口，网关服务直接响应给客户端
		  currentContext.setSendZuulResponse(false);  
	      currentContext.setResponseBody("userToken is null");
	      return null;
	  }
	  System.out.println("网关服务器端口号:"+serverPort);
	  //正常调用其它服务
	return null; 
	}

	@Override//判断过滤器是否生效。
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override//过滤器执行顺序,当一个请求在同一个阶段的时候，存在多个过滤器的时候，多个过滤器的执行顺序问题
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override//过滤器类型 pre 表示在请求之前
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
