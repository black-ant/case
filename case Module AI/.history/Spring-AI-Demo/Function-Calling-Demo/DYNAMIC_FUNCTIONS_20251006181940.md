# 动态函数注册指南

这个文档详细介绍如何在运行时动态注册、管理和使用函数。

## 🎯 什么是动态函数注册？

动态函数注册允许你在应用运行时：
- ✅ 动态添加新函数
- ✅ 动态删除函数
- ✅ 查询已注册的函数
- ✅ 灵活组合函数使用

## 🚀 快速开始

### 1. 批量注册示例函数

```bash
curl -X POST http://localhost:8080/api/dynamic/register/batch
```

这会注册 5 个示例函数：
- `greetUser` - 问候用户
- `getCurrentTime` - 获取当前时间
- `getRandomNumber` - 生成随机数
- `getStringLength` - 获取字符串长度
- `toUpperCase` - 转换为大写

### 2. 使用动态函数对话

```bash
curl -X POST http://localhost:8080/api/dynamic/chat/all \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好，我叫张三"
  }'
```

AI 会自动调用 `greetUser` 函数。

## 📋 API 接口详解

### 1. 注册预定义函数

```bash
curl -X POST http://localhost:8080/api/dynamic/register/example \
  -H "Content-Type: application/json" \
  -d '{
    "functionName": "myGreeting",
    "description": "向用户问候",
    "functionType": "greeting"
  }'
```

