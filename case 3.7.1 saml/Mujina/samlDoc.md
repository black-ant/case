# SAML 文档

## 基于该项目的部署

```JAVA
// 运行 IDP
mvn clean install
cd mujina-idp
mvn spring-boot:run
    
// Run the SP
mvn clean install
cd mujina-sp
mvn spring-boot:run
    
// 生成密钥对
openssl req -subj '/O=Organization, CN=Wuhan/' -newkey rsa:2048 -new -x509 -days 3652 -nodes -out Wuhan.crt -keyout Wuhan.pem    
    
// 格式化密钥
openssl pkcs8 -nocrypt  -in mujina.pem -topk8 -out mujina.der    
    
```

### openssl 生成密钥对

```java
openssl req -subj '/O=Organization, CN=Wuhan/' -newkey rsa:2048 -new -x509 -days 3652 -nodes -out Wuhan.crt -keyout Wuhan.pem    

// 但是 , 通常会碰到以下的问题 :
// 参考地址 : https://blog.csdn.net/sunhuansheng/article/details/82218678
1 openssl 不存在该命令
	Step 1 : 准备 Perl Windows 环境
		https://www.activestate.com/products/perl/downloads/    
	Step 2 : 下载 OpenSSL.exe 版本 (我这里下载的第二个63M的)
         http://slproweb.com/products/Win32OpenSSL.html
	
```



## 注意事项

```

```

