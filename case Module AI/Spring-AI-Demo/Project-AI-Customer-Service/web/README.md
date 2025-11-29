# AI 智能客服系统 - Web 前端

基于 React 18 + Ant Design 5 构建的现代化 Web 前端应用。

## 功能特性

- 🤖 **智能对话** - 实时 AI 客服对话界面
- 📊 **API 监控** - 实时监控 API Key 使用情况和健康状态
- 🧪 **API 测试** - 单次测试、并发测试、错误模拟

## 技术栈

- React 18.2
- React Router 6.3
- Ant Design 5.6
- Axios 1.4
- Recharts 2.7
- Moment.js 2.29

## 快速开始

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm start
```

访问 http://localhost:3000

### 构建生产版本

```bash
npm run build
```

## 项目结构

```
web/
├── public/              # 静态资源
│   └── index.html      # HTML 模板
├── src/
│   ├── components/     # 公共组件
│   │   └── ErrorBoundary.js
│   ├── pages/          # 页面组件
│   │   ├── ChatPage.js      # 对话页面
│   │   ├── MonitorPage.js   # 监控页面
│   │   └── TestPage.js      # 测试页面
│   ├── App.js          # 主应用组件
│   ├── App.css         # 应用样式
│   ├── index.js        # 入口文件
│   └── index.css       # 全局样式
└── package.json        # 项目配置
```

## 配置说明

### 后端代理

在 `package.json` 中配置了代理，所有 `/api` 请求会转发到后端服务：

```json
"proxy": "http://localhost:8080"
```

### 环境变量

可创建 `.env` 文件配置环境变量：

```
REACT_APP_API_URL=http://localhost:8080
```

## 页面说明

### 智能客服页面 (/)

- 实时对话界面
- 会话管理
- 快捷问题
- 消息历史

### API 监控页面 (/monitor)

- 系统健康率
- Key 使用统计
- 实时状态监控
- 自动刷新 (30秒)

### API 测试页面 (/test)

- 单次 API 测试
- 并发压力测试
- 错误模拟测试
- 统计数据重置

## 开发说明

### 添加新页面

1. 在 `src/pages/` 创建新组件
2. 在 `App.js` 中添加路由
3. 在侧边栏菜单中添加入口

### 样式定制

- 全局样式: `src/index.css`
- 应用样式: `src/App.css`
- 组件样式: 使用 inline styles 或 CSS Modules

## 常见问题

### 端口冲突

如果 3000 端口被占用，可以设置环境变量：

```bash
set PORT=3001
npm start
```

### 代理不工作

确保后端服务已启动在 8080 端口，或修改 `package.json` 中的 proxy 配置。

## 浏览器支持

- Chrome (推荐)
- Firefox
- Safari
- Edge

## License

MIT
