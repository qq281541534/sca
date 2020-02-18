# Getting Started

### mvn命令

1.mvn package 会执行 ```dockerfile:build``` 和 ```dockerfile:tag``` 命令

    * 如果package时不想打包Image可以这样跟参数：
```
    mvn clean package -Ddockerfile.skip
    
    * dockerfile.skip	
    * dockerfile.build.skip
    * dockerfile.tag.skip	
    * dockerfile.push.skip
    # 不能直接运行build命令，因为Dockerfile中的ARG参数由POM传递，所以需要使用mvn package命令进行镜像的构建
    # docker build -t hub.harbor.com:8850/sca/user-centent:0.0.1-SNAPSHOT .	
```

2.mvn deploy 会执行 ``dockerfile:push`` 命令
```
# mvn deploy会直接push，但是有个bug暂时没解决
docker push hub.harbor.com:8850/sca/user-centent:0.0.1-SNAPSHOT
```

3.运行镜像
```
docker run --name sca-user-center -itd hub.harbor.com:8850/sca/user-center:0.0.1-SNAPSHOT \
 --nacos.address=192.168.0.103 --zipkin.address=192.168.0.103 --rocketmq.address=192.168.0.103 --mysql.address=192.168.0.103
```