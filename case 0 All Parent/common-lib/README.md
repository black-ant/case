# Common Library 通用工具库

## 项目说明

这是一个通用工具类库，封装了常用的工具方法，可被其他项目引用。

## 工具类列表

| 工具类 | 说明 |
|--------|------|
| `CommonStringUtils` | 字符串处理工具，支持模板替换、驼峰/下划线转换 |
| `DateUtil` | 日期时间工具，线程安全的格式化和解析 |
| `FileUtils` | 文件操作工具，目录创建和检查 |
| `HttpClientUtils` | HTTP 客户端工具，支持 GET/POST 和 HTTPS 无证书请求 |
| `IDUtils` | ID 生成工具，随机 ID、邮箱、手机号生成 |
| `JexlUtils` | JEXL 表达式引擎工具，模板表达式求值 |
| `JSONUtils` | JSON 解析工具 |
| `ObjectUtils` | 对象类型判断工具 |
| `ReadUtils` | 文件读取工具，按行读取 |
| `ReflectionUtils` | 反射工具，字段/方法操作、类扫描 |
| `TemplateResolve` | 模板解析工具，占位符替换 |
| `WriteUtils` | 文件写入工具，支持图片压缩 |

## 快速开始

### 引入依赖

```xml
<dependency>
    <groupId>com.gang</groupId>
    <artifactId>com-gang-study-common-lib</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 使用示例

```java
// 字符串驼峰转下划线
String result = CommonStringUtils.humpToLine("userName"); // user_name

// 日期格式化
String dateStr = DateUtil.formatDateTime(new Date()); // 2024-01-01 12:00:00

// HTTP 请求
String response = HttpClientUtils.doGet("https://api.example.com/data");

// 模板解析
String template = "Hello, ${name}!";
Map<String, Object> params = Map.of("name", "World");
String output = TemplateResolve.resolveByMap(template, params); // Hello, World!
```

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Apache HttpClient
- Apache Commons (Lang3, Text, Collections4, JEXL3)
- Fastjson2
- Hutool

## 注意事项

- `HttpClientUtils.buildSSLCloseableHttpClient()` 信任所有证书，仅用于测试环境
- `DateUtil` 使用 ThreadLocal 保证线程安全
- 所有工具类设计为无状态，可安全并发使用

