FROM 127.0.0.1:8850/library/java
MAINTAINER TheOne 281541534@qq.com

COPY target/dubbo-consumer-0.0.1-SNAPSHOT.jar /usr/src/myapp/dubbo-consumer.jar

WORKDIR /usr/src/myapp

ENTRYPOINT ["java", "-jar", "dubbo-consumer.jar"]