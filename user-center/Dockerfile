FROM hub.harbor.com:8850/library/java
MAINTAINER TheOne 281541534@qq.com

ARG JAR_FILE
COPY target/${JAR_FILE} /app.jar

WORKDIR /

ENTRYPOINT ["java", "-jar", "app.jar"]