@echo on
title new_sso
color 0B 
set build=0

rem 开始执行Eureka 启动流程
@echo  ------> Cloud Eureka Build Install : date
cd ./eureka
if %build%==1 call mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

@echo  ------> Eureka Run
cd ./target
cmd /c start java -jar base-eureka-0.0.1-SNAPSHOT.jar > eureka.log 2>&1 &
@echo  ------> Eureka Run Over


rem 开始执行Service 启动流程
@echo  ------> Cloud Build Service
cd ../../service
if %build%==1 call mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
@echo  ------> Service Run
cd ./target
cmd /c start java -jar demo-0.0.1-SNAPSHOT.jar > demo.log 2>&1 &

@echo off
pause;