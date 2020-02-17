#!/usr/bin/env bash

mvn package -DskipTests

docker build -t 127.0.0.1:8850/sca/gateway:latest .


#docker run --name sca-gateway -itd -p 8040:8040 127.0.0.1:8850/sca/gateway:latest  --nacos.address=192.168.0.103 --zipkin.address=192.168.0.103