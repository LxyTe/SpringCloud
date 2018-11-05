

 #  SpringCloud 笔记

        
      说到SpringCloud大家肯定想到的是微服务这个词语，目前这个词语在java界出现的频率那个相当之高的，一个合格的程序员肯定是要会微服务的。
   
  ##### 什么是微服务？
  
       微服务架是从SOA架构演变过来，比SOA架构粒度会更加精细，让专业的人去做专业的事情（专注），目的提高效率，每个服务于服务之间互不影响，微服务架构中，每个服务必须独立部署，互不影响，微服务架构更加体现轻巧、轻量级，是适合于互联网公司敏捷开发

  ##### 微服务架构特征

   微服务架构倡导应用程序设计程多个独立、可配置、可运行和可微服务的子服务。服务与服务通讯协议采用Http协议，使用restful风格API形式来进行通讯，数据交换格式轻量级json格式通讯，整个传输过程中，采用二进制，所以http协议可以跨语言平台，并且可以和其他不同的语言进行相互的通讯，所以很多开放平台都采用http协议接口。(SOA是基于xml加http的形式进行通信的)
 
   #####  springcloud优势
     
       SpringCloud是基于SpringBoot基础之上开发的微服务框架，SpringCloud是一套目前非常完整的微服务解决方案框架，其内容包含
      服务治理、注册中心、配置管理、断路器、智能路由、微代理、控制总线、全局锁、分布式会话等。

    SpringCloud config 分布式配置中心
    SpringCloud netflix 核心组件
    Eureka:服务治理  注册中心
    Hystrix:服务保护框架
    Ribbon:客户端负载均衡器
    Feign：基于ribbon和hystrix的声明式服务调用组件
    Zuul: 网关组件,提供智能路由、访问过滤等功能
  
   执行原理图  ![兔兔](https://github.com/LxyTe/SpringCloud/blob/master/sringCloud.png)
