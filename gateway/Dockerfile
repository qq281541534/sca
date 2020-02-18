FROM 127.0.0.1:8850/library/java
MAINTAINER TheOne 281541534@qq.com

ARG JAR_FILE
COPY target/${JAR_FILE} /app.jar

WORKDIR /

ENTRYPOINT ["java", "-jar", "app.jar"]