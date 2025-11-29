import axios from 'axios';
import { message } from 'antd';

// 创建 axios 实例
const api = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? '/api' : 'http://localhost:8080/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器
api.interceptors.request.use(
  (config) => {
    // 可以在这里添加认证 token 等
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    console.error('API Error:', error);
    
    if (error.response) {
      // 服务器返回错误状态码
      const { status, data } = error.response;
      
      switch (status) {
        case 400:
          message.error('请求参数错误');
          break;
        case 401:
          message.error('未授权访问');
          break;
        case 403:
          message.error('禁止访问');
          break;
        case 404:
          message.error('请求的资源不存在');
          break;
        case 500:
          message.error('服务器内部错误');
          break;
        default:
          message.error(data?.message || '请求失败');
      }
    } else if (error.request) {
      // 网络错误
      message.error('网络连接失败，请检查网络设置');
    } else {
      // 其他错误
      message.error('请求失败: ' + error.message);
    }
    
    return Promise.reject(error);
  }
);

// API 方法
export const chatApi = {
  // 创建会话
  createSession: (data) => api.post('/chat/session', data),
  
  // 发送消息
  sendMessage: (data) => api.post('/chat', data),
  
  // 流式聊天
  sendStreamMessage: (data) => api.post('/chat/stream', data),
  
  // 结束会话
  endSession: (sessionId) => api.post(`/chat/session/${sessionId}/end`),
};

export const monitorApi = {
  // 获取监控面板数据
  getDashboard: () => api.get('/monitor/dashboard'),
  
  // 获取使用统计
  getUsageStats: () => api.get('/monitor/usage'),
  
  // 获取健康状态
  getHealthStatus: () => api.get('/monitor/health'),
  
  // 切换 Chat Key
  switchChatKey: () => api.post('/monitor/switch/chat'),
  
  // 切换 Embeddings Key
  switchEmbeddingsKey: () => api.post('/monitor/switch/embeddings'),
};

export const testApi = {
  // 单次测试
  singleTest: (data) => api.post('/test/single', data),
  
  // 并发测试
  concurrentTest: (data) => api.post('/test/concurrent', data),
  
  // 模拟错误
  simulateError: (data) => api.post('/test/simulate-error', data),
  
  // 重置统计
  resetStats: () => api.post('/test/reset-stats'),
  
  // 获取状态
  getStatus: () => api.get('/test/status'),
};

export default api;