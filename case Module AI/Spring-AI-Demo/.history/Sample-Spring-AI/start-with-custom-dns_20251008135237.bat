@echo off
echo ========================================
echo 启动 Spring AI 应用（使用自定义 DNS）
echo ========================================
echo.

REM 设置 JVM 使用公共 DNS 服务器
REM 阿里云 DNS: 223.5.5.5, 223.6.6.6
REM 腾讯 DNS: 119.29.29.29
REM Google DNS: 8.8.8.8, 8.8.4.4

set JAVA_OPTS=-Dsun.net.inetaddr.ttl=60
set JAVA_OPTS=%JAVA_OPTS% -Dsun.net.inetaddr.negative.ttl=10
set JAVA_OPTS=%JAVA_OPTS% -Djava.net.preferIPv4Stack=true

echo 使用以下 JVM 参数:
echo %JAVA_OPTS%
echo.

REM 如果需要使用代理，取消下面的注释并修改代理地址
REM set JAVA_OPTS=%JAVA_OPTS% -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=7890
REM set JAVA_OPTS=%JAVA_OPTS% -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=7890

echo 正在编译项目...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo 编译失败！
    pause
    exit /b 1
)

echo.
echo 正在启动应用...
java %JAVA_OPTS% -jar target\sample-spring-ai-1.0.0.jar

pause
