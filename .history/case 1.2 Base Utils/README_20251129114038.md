# Base Utils - 基础工具类示例

## 项目说明

这是一个基础工具类示例项目，封装了常用的系统操作和工具方法。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Lombok

## 功能列表

### WindowsUtils 系统工具类

| 方法 | 说明 |
|------|------|
| `getOsName()` | 获取操作系统名称 |
| `getOsVersion()` | 获取操作系统版本 |
| `getOsArch()` | 获取系统架构 |
| `getJavaVersion()` | 获取 Java 版本 |
| `getUserHome()` | 获取用户主目录 |
| `getWorkingDirectory()` | 获取当前工作目录 |
| `isWindows()` | 判断是否为 Windows |
| `isLinux()` | 判断是否为 Linux |
| `isMac()` | 判断是否为 Mac |
| `executeCommand(cmd)` | 执行系统命令 |
| `getAvailableProcessors()` | 获取 CPU 核心数 |
| `getMaxMemoryMB()` | 获取 JVM 最大内存 |
| `getTotalMemoryMB()` | 获取 JVM 已分配内存 |
| `getFreeMemoryMB()` | 获取 JVM 空闲内存 |
| `fileExists(path)` | 检查文件是否存在 |
| `isDirectory(path)` | 检查是否为目录 |

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

启动后会自动打印系统信息：

```
=== System Information ===
OS Name: Windows 10
OS Version: 10.0
OS Arch: amd64
Java Version: 11.0.x
User Home: C:\Users\username
Working Directory: D:\projects\base-utils
===========================
```

### 使用示例

```java
// 获取系统信息
String osName = WindowsUtils.getOsName();
String javaVersion = WindowsUtils.getJavaVersion();

// 判断操作系统
if (WindowsUtils.isWindows()) {
    // Windows 特定逻辑
}

// 执行命令
String result = WindowsUtils.executeCommand("echo Hello");

// 获取内存信息
long maxMemory = WindowsUtils.getMaxMemoryMB();
long freeMemory = WindowsUtils.getFreeMemoryMB();

// 文件操作
boolean exists = WindowsUtils.fileExists("/path/to/file");
boolean isDir = WindowsUtils.isDirectory("/path/to/dir");
```

## 项目结构

```
src/main/java/com/gang/study/utils/demo/
├── DemoApplication.java          # 启动类
└── utils/
    └── WindowsUtils.java         # 系统工具类

src/test/java/com/gang/study/utils/demo/
└── DemoApplicationTests.java     # 测试类
```

## 运行测试

```bash
mvn test
```

## 扩展建议

可以继续添加以下工具类：

- `StringUtils` - 字符串处理工具
- `DateUtils` - 日期时间工具
- `FileUtils` - 文件操作工具
- `NetworkUtils` - 网络工具
- `EncryptUtils` - 加密解密工具

## 学习要点

1. **System.getProperty()** - 获取系统属性
2. **Runtime.getRuntime()** - 获取运行时信息和执行命令
3. **ApplicationRunner** - Spring Boot 启动后执行逻辑
4. **跨平台兼容** - 根据操作系统选择不同的命令执行方式

