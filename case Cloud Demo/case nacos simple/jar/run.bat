@echo off
title new_sso
color 0B 
start cmd /c "java -jar com-gang-nacos-discovery-1.jar --server.port=9087 &" 
start cmd /c "java -jar com-gang-nacos-discovery-2.jar --server.port=9085 &"
start cmd /c "java -jar com-gang-nacos-discovery-3.jar --server.port=9083 &"
pause;