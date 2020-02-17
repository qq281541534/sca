#!/usr/bin/env bash

mvn package

docker build -t 127.0.0.1:8850/sca/dubbo-consumer:latest .


#docker run --name sca-dubbo-consumer -itd -p 8099:8099 127.0.0.1:8850/sca/dubbo-consumer:latest \
# --nacos.address=192.168.0.103 --zipkin.address=192.168.0.103

# http://localhost:8099/findById/1