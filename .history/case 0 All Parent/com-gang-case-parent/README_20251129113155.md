# Case Parent POM

## 项目说明

这是所有案例项目的父 POM，用于统一管理依赖版本和公共配置。

## 功能

- **统一 Spring Boot 版本**: 2.7.18 (LTS)
- **统一 Java 版本**: 11
- **统一依赖版本管理**: 避免版本冲突和安全漏洞

## 管理的依赖

| 依赖 | 版本 | 说明 |
|------|------|------|
| commons-lang3 | 3.14.0 | Apache 字符串/对象工具 |
| commons-text | 1.11.0 | Apache 文本处理工具 |
| commons-collections4 | 4.4 | Apache 集合工具 |
| commons-jexl3 | 3.3 | Apache 表达式语言 |
| fastjson2 | 2.0.43 | 阿里 JSON 处理（替代有漏洞的 v1） |
| hutool | 5.8.22 | 国产工具库 |

## 使用方式

在子项目的 `pom.xml` 中引用此父 POM：

```xml
<parent>
    <groupId>com.gang.case</groupId>
    <artifactId>com-gang-case-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <relativePath>../case 0 All Parent/com-gang-case-parent/pom.xml</relativePath>
</parent>
```

## 版本更新记录

- **2024-11**: 升级到 Spring Boot 2.7.18 + Java 11，更新所有依赖到最新安全版本

