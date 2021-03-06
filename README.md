

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
   
   ##### 服务治理SpringCloud Eureka

     什么是服务治理？
     在传统rpc远程调用中，服务与服务依赖关系，管理比较复杂，所以需要使用服务治理，管理服务与服务之间依赖关系，
     可以实现服务调用、负载均衡、容错等，实现服务发现与注册
     服务注册与发现(和dubbo很像，dubbo依赖于zookeeper)！
      在服务注册与发现中，有一个注册中心，当服务器启动的时候，会把当前自己服务器的信息 比如 服务地址通讯地址等以别名方式注册到注册中心上。
    另一方（消费者|服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址，让后在实现本地rpc调用远程。
    
   ##### 搭建注册中心
     pom文件依赖
       <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
     yml 配置文件
     server:
        port: 1616

    eureka:
      instance:
         hostname: 127.0.0.1
      client:
         registerWithEureka: false   是否将自己注册自己（eureka集群的时候可用）
 
 fetchRegistry: false              检测自己
      serviceUrl:
         defaultZone: http://127.0.0.1:1616/eureka/     要注册的ip和端口，连接的是注册中心的ip和端口

    spring:
       application:
          name: eureka   //在注册中心中的名字
   @EnableEurekaServer  此注解就表示 这是一个eureka注册中心 在springboot Main方法所在的类上面加上即可 启动成功后 输入 ip:port  可进入eureka注册中心界面（如果提示404，说明jar依赖包有问题（冲突）。）
   
   ##### 服务的提供者
    pom文件和上面的一样，不需要加信息的依赖
   @EnableEurekaClient  //注册服务或消费 ，表示此项目是一个服务的提供者或消费者 ,在springboot Main方法所在的类上面加上即可
      
         配置文件配置如下
          server:
               port: 1617

         eureka:
             instance:
               hostname: 127.0.0.1
        client:
              serviceUrl:
                defaultZone: http://127.0.0.1:1616/eureka/
      # 集群使用  http://127.0.0.1:1616/eureka/,http://127.0.0.1:1615/eureka/   表示将服务注册到两个注册中心

       spring:
            application:
                  name: hi   此值比较重要，表示 服务的提供者在注册中心中注册的服务名称
            这样配置之后我们就会把 controller中的内容，注册到注册中心中
   #####  服务的消费者
      pom文件和上面一样
      server:
          port: 1619

          eureka:
               instance:
                   hostname: 127.0.0.1
            client:
                  #是否将自己注册到eureka上,作为消费者的时候要为true
                    registerWithEureka: true
                    #检索服务
               fetchRegistry: true
                   serviceUrl:
                             defaultZone: http://127.0.0.1:1616/eureka/

              spring:
                   application:
                           name: order    服务的消费者名称
   服务消费者代码[查看代码](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-parent/springcloud-order/src/main/java/com/dist/app/controller/OrderController.java)
   
  ## 高可用注册中心 
  
      在微服务中，注册中心非常核心，可以实现服务治理，如果一旦注册出现故障的时候，可能会导致整个微服务无法访问，
      在这时候就需要对注册中心实现高可用集群模式
      
      Eureka高可用原理
     默认情况下Eureka是让服务注册中心，不注册自己
  
     register-with-eureka: true   改成true表示注册自己为注册中心
    fetch-registry: true
    注意效果 注册的名字spring:
                         application:
                                   name: eureka      ##集群的时候这个名字一定要相同 
    Eureka高可用实际上将自己作为服务向其他服务注册中心注册自己，这样就可以形成一组相互注册的服务注册中心，从而实现服务清单的互相同步，
    达到高可用效果。 
       
   可参考代码springcloud-server 和springcloud-server2搭建成一个集群的注册中心，但是集群的时候还是建议以3台注册中心为集群
   集群的时候要注意
          消费者还和以前一样写，如果一个注册中心挂掉之后，剩下注册中心的会顶上，但是大概需要30-120S的服务同步时间。
          
  #####   服务消费者模式
  ###### 获取服务
     消费者启动的时候，使用服务别名，会发送一个rest请求到服务注册中心获取对应的服务信息，让后会缓存到本地jvm客户端中，同时客户端每隔30秒从服务器上更新一次。
     可以通过 fetch-inte vall-seconds=30参数进行修以通过eureka.client .registry该参数默认值为30， 单位为秒 
      这样在服务的注册者关闭（或者挂掉）之后，服务的消费者还可以进行访问服务，直接服务从缓存中被删除。
  ###### 服务下线
    在系统运行过程中必然会面临关闭或重启服务的某个实例的情况，在服务关闭期有我们自然不希望客户端会继续调用关闭了的实例。所以在客户端程序中，当服务实例过正常的关闭操作时，它会触发一个服务下线的REST请求给Eureka Server, 告诉服务日中心:“我要下线了”。服务端在接收到请求之后，将该服务状态置为下线(DOWN)，井该下线事件传播出去。
  #####  服务注册模式
  
 ###### 失效剔除
      有些时候，我们的服务实例并不一定会正常下线，可能由于内存溢出、网络故障气因使得服务不能正常工作，而服务注册中心并未收到“服务下线”的请求。为了从服务表中将这些无法提供服务的实例剔除，Eureka Server 在启动的时候会创建一个定时任多默认每隔一一段时间(默认为60秒)将当前清单中超时(默认为90秒)没有续约的服务除出去

###### 自我保护
   ![兔兔](https://github.com/LxyTe/SpringCloud/blob/master/Eureka%E8%87%AA%E6%88%91%E4%BF%9D%E6%8A%A4.png)
        
        实际上，该警告就是触发了Eureka Server的自我保护机制。之前我们介绍过，服务注册到Eureka Server之后，会维护个心跳连接， 告诉Eureka Server自己还活 着。Eureka Server在运行期间，会统计心跳失败的比例在15分钟之内是否低于85%如果出现低于的情况单机调试的时候很容易满足，实际在生产环境上通常是由于网络不稳定导致)，EuServer会将当前的实例注册信息保护起来，让这些实例不会过期，尽可能保护这些注册信-息。但是，在这段保护期间内实例若出现问题，那么客户端很容易拿到实际已经不存服务实例，会出现调用失败的情况，所以客户端必须要有容错机制，比如可以使用请使用重试、断路器等机制。
     由于本地调试很容易触发注册中心的保护机制，这会使得注册中心维护的服务实仍那么准确。所以，我们在本地进行开发的时候，可以使用eureka . server . enablself preservation=false参数来关闭保护机制，以确保注册中心可以将不可用的例正确剔除。
  说白了就是自我保护机制开启的时候，服务就无法被剔除了，失效的也不会被剔除，这样可以保证在生产环境中，一些网络故障原因，导致误删服务。
  ###### 在注册中心所在项目的配置文件中配置
   #关闭自我保护机制，默认是开启的  (自我保护机制就是不踢,等待重试连接)
     server:
    # 测试时关闭自我保护机制，保证不可用服务及时踢出
       enable-self-preservation: false
    ##剔除失效服务间隔
         eviction-interval-timer-in-ms: 2000   2秒查一次
   
  ###### 然后在消费者服务的配置文件中配置
      # 心跳检测检测与续约时间   (默认是开启的，但是心跳时间为90s)
   测试时将值设置设置小些，保证服务关闭后注册中心能及时踢出服务
      instance:
     ###Eureka客户端向服务端发送心跳的时间间隔，单位为秒（客户端告诉服务端自己会按照该规则）  
    lease-renewal-interval-in-seconds: 1
    ####Eureka服务端在收到最后一次心跳之后等待的时间上限，单位为秒，超过则剔除（客户端告诉服务端按照此规则等待自己）
    lease-expiration-duration-in-seconds: 2    
    
  ##### 使用zookeeper做注册中心
      springboot2.0以上集成zookeeper的时候要使用zookeeper3.5以上的版本才能兼容
        pom 文件
        	<!-- SpringBoot整合zookeeper -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
		</dependency>
   相关代码[查看](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-parent/springcloud-zookeeper/src/main/java/com/dist/app/app.java)
  applicaiton.yml 配置和使用eureka时候的配置一样，就是ip端口改成zookeeper的就可以了
  #####  使用consul做注册中心
   pom文件
       
	 <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-consul-discovery</artifactId>
  	</dependency>
   其它的配置都和zookeeper的配置的一样
     
        @EnableDiscoveryClient 与@EnableEurekaClient区别
	1，@EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现； 适合于consul、zookeeper注册中心
        2，@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用
	public String getServiceUrl(String name) {
		List<ServiceInstance> list = discoveryClient.getInstances(name);//可得到某注册中心里面的全部注册节点（此模板使用与consul和zookeeper）
		if (list != null && !list.isEmpty()) {
			return list.get(0).getUri().toString();
		}
		return null;
	}
   ##### eureka zookeeper consul 3个注册中心的区别 （具体的使用可根据业务选址，这里只做简单比较）
      
       分布式系统的三大原则 CAP理论(Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可兼得)，所以
       大都是从中选出两个实现
       
       Consul保证了CP 肯定要保证P呀，要不然一个网络波动就玩完
	服务注册相比 Eureka 会稍慢一些。因为 Consul 的 raft 协议要求必须过半数的节点都写入成功才认为注册成功 Leader 挂掉时，重新选举期间整个 Consul 不可用。保证了强一致性但牺牲了可用性。

	Eureka 保证高可用(A)和最终一致性(AP)：

	服务注册相对要快，因为不需要等注册信息 replicate 到其他节点，也不保证注册信息是否 replicate 成功当数据出现不一致时，虽然A, B上的注册信息不完全相同，但每个Eureka节点依然能够正常对外提供服务，这会出现查询服务信息时如果请求A查不到，但请求B就能查到。如此保证了可用性但牺牲了一致性。

      zookeeper（CP）
       是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30 ~ 120s, 且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的
	
  ##### 负载均衡器(Ribbon负载均衡客户端)
     在SpringCloud中Ribbon负载均衡客户端，会从eureka注册中心服务器端上获取服务注册信息列表，缓存到本地
      说到负载均衡，肯定要比较 Rabbon和Nginx的却别
        
	nginx是客户端所有请求统一交给nginx，由nginx进行实现负载均衡请求转发，属于服务器端负载均衡。
     既请求有nginx服务器端进行转发
     
       Ribbon是从eureka注册中心服务器端上获取服务注册信息列表，缓存到本地，让后在本地实现轮训负载均衡策略。
     既在客户端实现负载均衡
      
      应用场景区别
      
      Nginx适合于服务器端实现负载均衡 比如Tomcat ，Ribbon适合与在微服务中RPC远程调用实现本地服务负载均衡，比如Dubbo、SpringCloud中都是采用本地负载均衡
  原理图![x](https://github.com/LxyTe/SpringCloud/blob/master/Ribbon%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1%E5%8E%9F%E7%90%86.png)
   rabbion实现原理[查看代码](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-parent/springcloud-order/src/main/java/com/dist/app/controller/RabbonController.java)
   
   ##### 使用feign声明式客户端
   
   feign客户端调用是实际项目中的调用方法。实际项目中不推荐使用rest方式进行调用
   [具体区别比较](https://www.cnblogs.com/EasonJim/p/8321355.html)
   
   具体feign客户端的使用方式[查看项目](https://github.com/LxyTe/SpringCloud/tree/master/springcloud-te-parent)
   
   #### 上面使用maven聚合项目的形式搭建了一个使用feign客户端调用服务的例子
     
        下面的配置可以解决某个方法如果处理时间过长，造成连接丢失问题。
         ### 设置feign客户端超时 时间,springcloud默认支持ribbon是打开的
     ribbon:
        ##建立连接所用时间
      ReadTimeout: 5000 
            ## 建立连接后从服务端读取资源所用最大时间 
         ConnectTimeout: 5000      
   
   ##  Hystrix（服务保护机制）
     
        Hystrix  服务保护框架在项目中可做那些事情？
	1.断路器
	2.服务降级
	3.服务熔断
	4.服务隔离
	5.预防服务雪崩
      以上是实现高可用架构必须注意的问题 （高可用  就是应对分布式系统环境中的各种各样的问题，避免整个分布式系统被某个服务的故障给拖垮）
   
      服务雪崩介绍
          服务雪崩效应产生与服务堆积在同一个线程池中（一个线程池相当于一个Tomcat服务器等），因为所有的请求都是同一个线程池进行处理，这时候如果在高并发情况下，所有的请求全部访问同一个接（会导致用光线程池中的所有线程），这时候可能会导致其他服务没有线程进行接受请求，无法进行正常访问。如果雪崩过于严重就会让整个服务都无法进行访问。全部崩溃
	  这就是服务雪崩效应。（我们可以通过 服务的降级，熔断，隔离等机制来解决服务雪崩效应问题）
      
      服务降级介绍
      在高并发情况下，防止用户一直等待，使用服务降级方式（返回一个友好的提示直接给客户端，不会去处理请求，直接调用fallBack的方法）目的是为了用户体验友好。
      比如秒杀--------当前请求人数过多，请稍后重试
       12306--------- 您的前面有xxx人正在排队
        （在tomcat中没有线程进行处理客户端请求的时候，我们不应让客户的页面一直转圈圈 ）
	
      服务熔断
         服务熔断目的是为了保护服务，在高并发的情况下，如果请求达到了一个极限（可以自己设置阈（峰）值），如果请求超出了设置的阈值，自动开启服务保护功能，使用服务降级方式返回一个友好的提示。熔断机制和服务降级一起使用（相当于服务降级的一个策略）
     
     服务隔离（线程池隔离机制）
	  线程池隔离: 每个服务接口都有自己独立的线程池，每个线程池互不影响。(缺点 CPU使用率高)
	  在实际应用中，将一些并发量特别高的接口使用独立的线程池，将并发量少的接口使用同一个线程池这样就可以避免服务雪崩的问题。
	  当并发量高的接口到达峰值的时候还是使用服务熔断解决，其它接口由于使用了不同的线程池，所以还是可以正常访问的
	  
 关于@HystrixCommand 接口的使用(此接口很强大，默认实现了 服务隔离，降级，熔断)
   pom依赖
   
        <!-- hystrix断路器 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
		</dependency>
   
   [查看代码详细使用](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-te-parent/springcloud-te-servie-order-impl/src/main/java/com/dist/order/orderServiceImpl.java)
   #### 分布式配置中心(SpringCloud Config)
    使用分布式配置中心最大的好处就是在生产环境中，如有某个配置文件需要修改，可以直接在配置中心中修改，而不用在项目xml文件中，进行繁琐的修改。
    可以实现后台统一的配置管理。(可以实现不重启服务器，达到修改配置文件的效果)
    
   分布式配置中心框架
     
      阿波罗 (后续查看)     携程的分布式配置中心，有图形界面可管理配置文件信息  (配置的信息在DB中)
      xdiamond    也是一个分布式配置中心，有图形界面可管理配置文件信息 (配置的信息在DB中)
      springCloud Config 没有后台可管理，配置文件在版本控制器中(git/svn)
      Zookeeper   也可以实现分布式配置中心，持久节点+时间通知(以后有时间了可以学习一下
     
     1.1 原理
     先提交配置文件信息到Git等版本控制服务器上面，
     ConfigServer服务器从git服务器上面读取配置文件信息保存(相当于缓存，避免Client端直接连git服务器)
     ConfigClient从 ConfigServer服务器上面获取配置信息
     1.2分布式配置中心需要组件！
     1.2.1 web管理系统   后台可以使用图形化界面对配置进行CRUD等操作   (springCloud Config没有web页面)
     1.2.2 存放分布式配置文件服务器  (持久存储服务器,相当于mysql，oracle等)    (springCloud Config 使用版本控制器存放，这里使用git)
     1.2.3 ConfigServer 缓存配置文件服务器  (临时缓存存放 ， 相当于NOSQL) 
     1.2.4 ConfigClient 读取上面的配置文件信息
    
   #### 网关(api ,getway)
    概念：
     相当于客户端请求统一先被请求到网关服务器上面， 再由网关服务器进行转发到实际服务地址上，类似于Nginx

     作用
     1. 网关可以拦截客户端所有请求，对该请求进行 权限控制，日志管理，接口调用监控等。
     2.过滤器和网关的区别  过滤器适合于单个Tomcat服务器进行拦截请求。网关过滤整个微服务所有请求。
     
     api 接口
     如何设计一个接口?
     接口权限问题(开放接口|内部接口)丶考虑幂等性(确保多次操作，返回同一个相同的结果，误会因为用户的误操作产生数据的冗余)丶安全性(使用https)丶防止篡改数据(每个接口加上数字签名)丶使用网关的黑名单和白名单(下面会介绍)，  接口使用http协议+json格式 restful风格的目的是为了跨平台(接口底层走的是socket，socket走的是二进制数据，因此达到跨平台),考虑高并发(对接口服务实现，服务的降级，熔断，隔离，等，使用接口管理工具 如swagger)
      Nginx和Zuul区别
       相同点：
        两个都可以实现负载均衡，反向代理，过滤请求，实现网关效果。
       不通点：
       Nginx 采用C语言编写。
       Zuul采用 java编写
       
       Zuul负载均衡的实现： 采用rabbon+eureka 实现本地负载均衡
       Nginx  负载均衡实现：采用服务器端实现负载均衡。
       
       总结：Nginx比Zuul功能更加强大，Nginx可以整合一些脚本语言(适合服务器端负载均衡)
       Zuul 适合微服务网关。java底层编写。 
       
       Nginx+Zuul一起使用的时候，Nginx做服务器负载均衡，然后使用Zuul做网关拦截。
       pom依赖
         <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
         </dependency>

   相关application.yml
   [!查看相关配置](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-zuul/src/main/resources/application.yml)
    
    启动类相关配置
    @SpringBootApplication
    @EnableZuulProxy   //开启网关
    @EnableEurekaClient   //
    2.使用zuul搭建过滤器
     zuul搭建的过滤器和filter过滤器的作用是一致的，但是不同点在于 zuul的过滤器可以过滤整个微服务群，而filter只能过滤单个项目，总体来说zuul的功能更加强大.
     @Bean  //要在配置启动类中加上bean，要不然无法实现过滤效果
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}
     
  [!查看相关代码](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-zuul/src/main/java/com/example/demo/filter/TokenFilter.java)
  
    3.网关集群(搭建高可用网关)
   ![兔兔](https://github.com/LxyTe/SpringCloud/blob/master/zuul%E9%9B%86%E7%BE%A4.png)
    具体实现需要下载nginx 然后在nginx.conf中配置一下内容即可
    
     ####上游服务器 集群 默认网关配置,轮询机制
    upstream  backServer{
    server  127.0.0.1:81;表示监控的两个网关端口，具体情况可自己修改
    server  127.0.0.1:82;
    }

    server {
        listen       80;//自己启动端口
        server_name  te.mm.com;  这个名字可以为访问地址的名字，也可是localhost(默认的)
        location / {
           ### 指定上游服务器负载均衡服务器
	   proxy_pass  http://backServer/;
	   index   index.html index.htm;
        }
    }
 按照上面的配置我们访问服务地址为    http://te.mm.com/api-order/orderToMember   ,te.mm.com为nginx 的定义名字，api-order为网关协议名字，orderToMember为具体的服务名字
  #### 集成swagger
  pom依赖
    
           <!--整合swagger -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.6.1</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.6.1</version>
		</dependency>
     代码的配置方式也和以前一样。。。。。
    
   使用zuul管理接口文档,(当有多个微服务项目的时候，使用网关管理比较方便)
   pom文件 可以继续用上面的，也可以用spring boot依赖的
     
     <!-- 整合swagger-boot -->
         <dependency>
     		 <groupId>com.spring4all</groupId>
    		 <artifactId>swagger-spring-boot-starter</artifactId>
    		 <version>1.7.0.RELEASE</version>
        </dependency>
 	<dependency>
		<groupId>com.google.guava</groupId>
		<artifactId>guava</artifactId>
		<version>22.0</version>
	</dependency>
	<!-- https://mvnrepository.com/artifact/io.swagger/swagger-models -->
	<dependency>
 	  	<groupId>io.swagger</groupId>
	  	<artifactId>swagger-models</artifactId>
   	 	<version>1.5.21</version>
	</dependency>
	@EnableSwagger2Doc 并且在每个服务的启动类上加入此注解
	application.yml配置 扫描的包swagger: base-package: com.dist.member.impl
 
   swagger详细代码[查看](https://github.com/LxyTe/SpringCloud/blob/master/springcloud-zuul/src/main/java/com/example/demo/swaggerConfig.java)
	
  #### 消息总线等学完mq之后
