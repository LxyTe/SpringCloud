package com.dist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	@RequestMapping("/fi")
	public String dd(){
		return "ff";
	}
}
