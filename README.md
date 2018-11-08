

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
   获取服务
     消费者启动的时候，使用服务别名，会发送一个rest请求到服务注册中心获取对应的服务信息，让后会缓存到本地jvm客户端中，同时客户端每隔30秒从服务器上更新一次。
     可以通过 fetch-inte vall-seconds=30参数进行修以通过eureka.client .registry该参数默认值为30， 单位为秒 
      这样在服务的注册者关闭（或者挂掉）之后，服务的消费者还可以进行访问服务，直接服务从缓存中被删除。
  服务下线
    在系统运行过程中必然会面临关闭或重启服务的某个实例的情况，在服务关闭期有我们自然不希望客户端会继续调用关闭了的实例。所以在客户端程序中，当服务实例过正常的关闭操作时，它会触发一个服务下线的REST请求给Eureka Server, 告诉服务日中心:“我要下线了”。服务端在接收到请求之后，将该服务状态置为下线(DOWN)，井该下线事件传播出去。
  #####  服务注册模式
  失效剔除
      有些时候，我们的服务实例并不一定会正常下线，可能由于内存溢出、网络故障气因使得服务不能正常工作，而服务注册中心并未收到“服务下线”的请求。为了从服务表中将这些无法提供服务的实例剔除，Eureka Server 在启动的时候会创建一个定时任多默认每隔一一段时间(默认为60秒)将当前清单中超时(默认为90秒)没有续约的服务除出去

自我保护
        
        实际上，该警告就是触发了Eureka Server的自我保护机制。之前我们介绍过，服务注册到Eureka Server之后，会维护个心跳连接， 告诉Eureka Server自己还活 着。Eureka Server在运行期间，会统计心跳失败的比例在15分钟之内是否低于85%如果出现低于的情况单机调试的时候很容易满足，实际在生产环境上通常是由于网络不稳定导致)，EuServer会将当前的实例注册信息保护起来，让这些实例不会过期，尽可能保护这些注册信-息。但是，在这段保护期间内实例若出现问题，那么客户端很容易拿到实际已经不存服务实例，会出现调用失败的情况，所以客户端必须要有容错机制，比如可以使用请使用重试、断路器等机制。
     由于本地调试很容易触发注册中心的保护机制，这会使得注册中心维护的服务实仍那么准确。所以，我们在本地进行开发的时候，可以使用eureka . server . enablself preservation=false参数来关闭保护机制，以确保注册中心可以将不可用的例正确剔除。
  说白了就是自我保护机制开启的时候，服务就无法被剔除了，失效的也不会被剔除，这样可以保证在生产环境中，一些网络故障原因，导致误删服务。
  在 注册中心所在项目的配置文件中配置
   #关闭自我保护机制，默认是开启的  (自我保护机制就是不踢,等待重试连接)
     server:
    # 测试时关闭自我保护机制，保证不可用服务及时踢出
       enable-self-preservation: false
    ##剔除失效服务间隔
         eviction-interval-timer-in-ms: 2000   2秒查一次
   
   然后在消费者项目的配置文件中配置
      # 心跳检测检测与续约时间   (默认是开启的，但是心跳时间为90s)
   测试时将值设置设置小些，保证服务关闭后注册中心能及时踢出服务
      instance:
     ###Eureka客户端向服务端发送心跳的时间间隔，单位为秒（客户端告诉服务端自己会按照该规则）  
    lease-renewal-interval-in-seconds: 1
    ####Eureka服务端在收到最后一次心跳之后等待的时间上限，单位为秒，超过则剔除（客户端告诉服务端按照此规则等待自己）
    lease-expiration-duration-in-seconds: 2    
