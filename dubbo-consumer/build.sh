#!/usr/bin/env bash

mvn package

docker build -t 127.0.0.1:8850/sca/content-center:latest .


#docker run --name sca-content-center -itd 127.0.0.1:8850/sca/content-center:latest \
# --nacos.address=192.168.0.103 --zipkin.address=192.168.0.103 --rocketmq.address=192.168.0.103 --mysql.address=192.168.0.103 \
# --sentinel.dashboard.address=192.168.0.103