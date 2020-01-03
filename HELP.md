# Getting Started

## 项目启动总结
﻿
### 一、nacos

- 目录：tools/idea-workspace/spring-cloud-alibaba-demo/nacos/distribution/target/nacos-server-1.1.4/nacos

- 启动命令：``` bin/startup.sh -m standalone ```
- 地址：
    > [登陆http://127.0.0.1:8848/nacos/#/login](http://127.0.0.1:8848/nacos/#/login)用户名密码：nacos

- 停止命令：```  bin/shutdown.sh ```

### 二、sentinel

- 目录：tools/idea-workspace/spring-cloud-alibaba-demo/sentinel/Sentinel/sentinel-dashboard/target

- 启动命令：
```
    java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -Dsentinel.dashboard.auth.username=admin -Dsentinel.dashboard.auth.password=admin -jar sentinel-dashboard.jar
```
- 地址：
    > [登陆http://127.0.0.1:8848/nacos/#/login](http://localhost:8080/#/login) 用户名密码(默认)：sentinel



###三、roketmq

- 目录：tools/idea-workspace/spring-cloud-alibaba-demo/rocketmq-all-4.5.1-bin-release

- 启动命令：
 
    > server:    ```nohup sh bin/mqnamesrv & ```
    
    > broker:    ```nohup sh bin/mqbroker -n localhost:9876 & ```
    
    > 查看是否启动成功：```tail -200f nohup.out```

- 停止命令：

    > 停止broker:    ```sh bin/mqshutdown broker```
    
    > 停止server:    ```sh bin/mqshutdown namesrv```


### 四、rocketmq dashboard

- 目录：tools/idea-workspace/spring-cloud-alibaba-demo/rocketmq-externals/rocketmq-externals-master/rocketmq-console/target

- 启动命令：```java -jar rocketmq-console-ng-1.0.1.jar```

- 地址：

    > http://localhost:17890/#/topic

### 五、elasticsearch

- 目录：tools/idea-workspace/spring-cloud-alibaba-demo/elasticsearch/elasticsearch-6.8.2

- 启动命令：```bin/elasticsearch```

- 地址：http://localhost:9200/



### 六、zipkin

- 目录：tools/idea-workspace/spring-cloud-alibaba-demo/zipkin

- 启动命令：

    > zipkin:   
    ````STORAGE_TYPE=elasticsearch ES_HOSTS=localhost:9200 java -jar zipkin-server-2.12.9-exec.jar````
    
    > zipkin dependencies:  
    ````STORAGE_TYPE=elasticsearch ES_HOSTS=localhost:9200 java -jar zipkin-dependencies-2.3.2.jar````

- 地址：http://localhost:9411/



















