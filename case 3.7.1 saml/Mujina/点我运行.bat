@echo off
title run_saml2
color 0B 
cd ./mujina-idp
start cmd /c "mvn spring-boot:run"
cd ../
cd ./mujina-sp
start cmd /c "mvn spring-boot:run"
   
pause;