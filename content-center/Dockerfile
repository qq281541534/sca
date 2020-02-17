FROM 127.0.0.1:8850/library/java
MAINTAINER TheOne 281541534@qq.com

COPY target/user-center-0.0.1-SNAPSHOT.jar /usr/src/myapp/user-center.jar

WORKDIR /usr/src/myapp

ENTRYPOINT ["java", "-jar", "user-center.jar"]