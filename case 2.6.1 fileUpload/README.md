# File Upload Demo

## 项目说明

Spring Boot 文件上传下载示例，演示多种文件处理方式。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Thymeleaf
- Commons IO

## 功能列表

- 单文件上传
- 多文件上传
- 文件下载
- 上传进度监控

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 访问页面

- 上传页面：http://localhost:8080/pages/upload.html

### API 接口

| 接口 | 方法 | 说明 |
|------|------|------|
| /upload/upload | POST | 基础文件上传 |
| /upload/transferTo | POST | transferTo 方式上传 |
| /upload/multiple | POST | 多文件上传 |

## 配置说明

在 `application.properties` 中配置：

```properties
# 上传目录
file.upload.path=./uploads

# 单个文件最大大小
spring.servlet.multipart.max-file-size=10MB

# 请求最大大小
spring.servlet.multipart.max-request-size=100MB

# 是否延迟解析
spring.servlet.multipart.resolve-lazily=false
```

## 上传示例

### 使用 curl

```bash
# 单文件上传
curl -X POST http://localhost:8080/upload/upload \
  -F "file=@/path/to/file.txt"

# 多文件上传
curl -X POST http://localhost:8080/upload/multiple \
  -F "files=@file1.txt" \
  -F "files=@file2.txt"
```

### 使用 JavaScript

```javascript
const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('/upload/upload', {
    method: 'POST',
    body: formData
})
.then(response => response.json())
.then(data => console.log(data));
```

## 项目结构

```
src/main/java/com/fileupload/demo/
├── DemoApplication.java          # 启动类
├── config/
│   ├── MyConfig.java             # 通用配置
│   └── UploadConfig.java         # 上传配置
├── controller/
│   ├── UploadController.java     # 上传控制器
│   └── DownLoadController.java   # 下载控制器
└── to/
    └── DownloadTO.java           # 下载传输对象

src/main/resources/
├── static/
│   └── pages/
│       └── upload.html           # 上传页面
└── templates/
    └── index.html                # 首页模板
```

## 运行测试

```bash
mvn test
```

## 注意事项

- 生产环境应配置合适的文件大小限制
- 建议使用文件类型白名单验证
- 大文件上传考虑使用分片上传
- 敏感文件应存储在非 Web 可访问目录

