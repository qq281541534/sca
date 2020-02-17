FROM 127.0.0.1:8850/library/java
MAINTAINER TheOne 281541534@qq.com

COPY target/content-center-0.0.1-SNAPSHOT.jar /usr/src/myapp/content-center.jar

WORKDIR /usr/src/myapp

ENTRYPOINT ["java", "-jar", "content-center.jar"]