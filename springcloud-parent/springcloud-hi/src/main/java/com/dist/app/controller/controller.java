package com.dist.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

	@Value("${server.port}")
	private String port;

	@RequestMapping("/MM")
	public String dd(){
		return "我一定会得到你，不惜一切代价"+port;
	}
}
