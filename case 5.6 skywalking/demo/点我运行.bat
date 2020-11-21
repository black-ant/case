@echo off
title run_saml2
color 0B 
start cmd /c "java -jar -javaagent:D:\java\plugins\shywalking\agent\skywalking-agent.jar -Dskywalking.agent.service_name=stressTest1 -Dskywalking.collector.backend_service=localhost:11800 demo-0.0.1-SNAPSHOT.jar --server.port=8081 > log1.log &"
start cmd /c "java -jar -javaagent:D:\java\plugins\shywalking\agent\skywalking-agent.jar -Dskywalking.agent.service_name=stressTest2 -Dskywalking.collector.backend_service=localhost:11800 demo-0.0.1-SNAPSHOT.jar --server.port=8082 > log2.log &"
pause;