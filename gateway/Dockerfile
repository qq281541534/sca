FROM 127.0.0.1:8850/library/java
MAINTAINER TheOne 281541534@qq.com

COPY target/gateway-0.0.1-SNAPSHOT.jar /usr/src/myapp/gateway.jar

WORKDIR /usr/src/myapp

ENTRYPOINT ["java", "-jar", "gateway.jar"]