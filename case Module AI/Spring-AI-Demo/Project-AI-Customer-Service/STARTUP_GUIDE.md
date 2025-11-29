# 启动指南

## 🚀 快速启动

### 方式一：使用智能启动脚本（推荐）

```bash
start.bat
```

然后选择：
- `1` - 只启动后端
- `2` - 只启动前端
- `3` - 同时启动前后端

### 方式二：分别启动

**启动后端：**
```bash
start-backend.bat
```

**启动前端：**
```bash
start-web.bat
```

### 方式三：使用 IDE（推荐用于开发）

**后端：**
1. 用 IntelliJ IDEA 或 Eclipse 打开项目
2. 找到 `CustomerServiceApplication.java`
3. 右键 → Run

**前端：**
```bash
cd web
npm install
npm start
```

## 📋 环境要求

### 后端
- ✅ Java 17 或更高版本
- ✅ Maven 3.6+ （如果使用命令行启动）
- ✅ 或者使用 IDE（IntelliJ IDEA / Eclipse）

### 前端
- ✅ Node.js 16 或更高版本
- ✅ npm 或 yarn

## 🔧 环境检查

### 检查 Java
```bash
java -version
```
应该显示 Java 17 或更高版本

### 检查 Maven
```bash
mvn -version
```

### 检查 Node.js
```bash
node -v
npm -v
```

## 📦 安装依赖

### 后端依赖
Maven 会自动下载依赖，首次启动可能需要几分钟。

### 前端依赖
```bash
cd web
npm install
```

## 🌐 访问地址

启动成功后：
- **前端界面**: http://localhost:3000
- **后端 API**: http://localhost:8080

## ❌ 常见问题

### 问题 1: 'mvn' 不是内部或外部命令

**原因**: Maven 未安装或未配置环境变量

**解决方案：**

**方案 A: 安装 Maven**
1. 下载 Maven: https://maven.apache.org/download.cgi
2. 解压到任意目录（如 `C:\Program Files\Apache\maven`）
3. 添加到系统环境变量 PATH
4. 重启命令行窗口

**方案 B: 使用 IDE（推荐）**
1. 安装 IntelliJ IDEA Community（免费）
2. 打开项目
3. 等待 Maven 自动导入依赖
4. 运行 `CustomerServiceApplication`

### 问题 2: 'java' 不是内部或外部命令

**原因**: Java 未安装或未配置环境变量

**解决方案：**
1. 下载 Java 17: https://adoptium.net/
2. 安装时选择"添加到 PATH"
3. 重启命令行窗口
4. 验证: `java -version`

### 问题 3: 端口 8080 已被占用

**解决方案：**

**方案 A: 关闭占用端口的程序**
```bash
# 查看占用端口的进程
netstat -ano | findstr :8080

# 结束进程（替换 PID）
taskkill /F /PID <PID>
```

**方案 B: 修改端口**
编辑 `src/main/resources/application.properties`:
```properties
server.port=8081
```

### 问题 4: 端口 3000 已被占用

**解决方案：**
前端会自动提示使用其他端口，输入 `Y` 即可。

### 问题 5: 前端依赖安装失败

**解决方案：**
```bash
cd web

# 清理缓存
npm cache clean --force

# 删除 node_modules
rmdir /s /q node_modules

# 重新安装
npm install
```

### 问题 6: 后端启动失败 - 数据库连接错误

**原因**: 配置文件中的数据库配置不正确

**解决方案：**
本项目使用 H2 内存数据库，无需额外配置。如果仍有问题，检查 `application.properties`。

## 🎯 推荐启动方式

### 开发环境
使用 IDE 启动后端 + 命令行启动前端：
```bash
# 终端 1: 在 IDE 中启动后端

# 终端 2: 启动前端
cd web
npm start
```

### 演示/测试环境
使用启动脚本：
```bash
start.bat
# 选择 3 (同时启动前后端)
```

## 📚 更多帮助

- 查看 README.md 了解项目详情
- 查看 API_CONFIG.md 了解配置管理
- 查看 VECTOR_STORE.md 了解向量存储
- 查看 MULTI_CONFIG_GUIDE.md 了解多配置管理

## 🆘 仍然无法启动？

1. 确认所有环境要求都已满足
2. 查看错误日志
3. 尝试使用 IDE 启动
4. 检查防火墙设置
5. 确认没有其他程序占用端口

---

**提示**: 首次启动可能需要下载依赖，请耐心等待。
