@echo
set other = message 3
rem 该文件用于自定义参数 ，并且设置参数 
rem cmd 运行 后 输入 设置参数.bat aaaaa bbbb
echo "param1 : " %1 "param2 : " %2


echo %other%
echo %JAVA_HOME%
echo "message is" %other%