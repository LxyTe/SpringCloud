package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
public class swaggerConfig  implements SwaggerResourcesProvider {

	public List<SwaggerResource>get(){
		List<SwaggerResource> resource=new ArrayList<>();
		resource.add(swaggerResource("te-mm-order","/api-order/v2/api-docs","2.0"));
		resource.add(swaggerResource("te-mm-member","/api-member/v2/api-docs","2.0"));
		return resource;
	}
	
	private SwaggerResource swaggerResource(String name,String location,String version){
		
		SwaggerResource swaggerResource=new SwaggerResource();
	     swaggerResource.setName(name);
	     swaggerResource.setLocation(location);
	     swaggerResource.setSwaggerVersion(version);
	    return swaggerResource;	     
	}
}
