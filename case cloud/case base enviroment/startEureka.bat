@echo off
cd ./eureka
mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ./eureka/target
start cmd /k "java -jar base-eureka-0.0.1-SNAPSHOT.jar > eureka.log 2>&1 &"

pause;