@echo off
title new_sso
color 0B 
cd ./eureka
call mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ./eureka/target
call java -jar base-eureka-0.0.1-SNAPSHOT.jar > eureka.log 2>&1 &
pause;