#!/usr/bin/env bash

mvn package -DskipTests

docker build -t 127.0.0.1:8850/sca/user-center:latest .


#docker run --name sca-user-center -itd 127.0.0.1:8850/sca/user-center:latest \
# --nacos.address=192.168.0.103 --zipkin.address=192.168.0.103 --rocketmq.address=192.168.0.103 --mysql.address=192.168.0.103