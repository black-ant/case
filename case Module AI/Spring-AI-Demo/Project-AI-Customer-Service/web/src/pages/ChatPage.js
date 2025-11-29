import React, { useState, useEffect, useRef } from 'react';
import { 
  Input, 
  Button, 
  Card, 
  message, 
  Spin, 
  Tag, 
  Space,
  Divider,
  Tooltip,
  Switch,
  Modal,
  Form,
  InputNumber,
  Select
} from 'antd';
import { 
  SendOutlined, 
  UserOutlined, 
  RobotOutlined,
  ReloadOutlined,
  InfoCircleOutlined,
  SettingOutlined,
  ThunderboltOutlined,
  StopOutlined
} from '@ant-design/icons';
import axios from 'axios';
import moment from 'moment';

const { TextArea } = Input;
const { Option } = Select;

const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [loading, setLoading] = useState(false);
  const [sessionId, setSessionId] = useState(null);
  const [sessionInfo, setSessionInfo] = useState(null);
  const [streamEnabled, setStreamEnabled] = useState(true);
  const [configModalVisible, setConfigModalVisible] = useState(false);
  const [aiConfig, setAiConfig] = useState({
    temperature: 0.7,
    maxTokens: 2000,
    model: 'gpt-3.5-turbo'
  });
  const messagesEndRef = useRef(null);
  const abortControllerRef = useRef(null);

  // 快捷问题
  const quickQuestions = [
    '你好，我想查询订单状态',
    '我的包裹什么时候能到？',
    '如何申请退款？',
    '我想投诉一个问题',
    '查询订单 ORD001',
    '物流跟踪 TRK123456'
  ];

  useEffect(() => {
    initializeSession();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const initializeSession = async () => {
    try {
      const customerId = 'user_' + Date.now();
      const customerName = '测试用户';
      
      const response = await axios.post('/api/customer-service/session', {
        customerId,
        customerName
      });
      
      setSessionId(response.data.sessionId);
      setSessionInfo({
        customerId,
        customerName,
        sessionId: response.data.sessionId,
        startTime: new Date()
      });
      
      // 添加欢迎消息
      setMessages([{
        id: 1,
        role: 'assistant',
        content: '您好！我是AI智能客服助手，很高兴为您服务。我可以帮您查询订单、处理退换货、解答产品问题等。请问有什么可以帮助您的吗？',
        timestamp: new Date(),
        type: 'welcome'
      }]);
      
    } catch (error) {
      console.error('Failed to initialize session:', error);
      message.error('初始化会话失败，请刷新页面重试');
    }
  };

  const sendMessage = async (messageText = inputValue) => {
    if (!messageText.trim() || !sessionId) return;

    const userMessage = {
      id: Date.now(),
      role: 'user',
      content: messageText,
      timestamp: new Date()
    };

    setMessages(prev => [...prev, userMessage]);
    setInputValue('');
    setLoading(true);

    if (streamEnabled) {
      await sendStreamMessage(messageText);
    } else {
      await sendNormalMessage(messageText);
    }
  };

  const sendNormalMessage = async (messageText) => {
    try {
      const response = await axios.post('/api/customer-service/chat', {
        sessionId,
        customerId: sessionInfo.customerId,
        message: messageText
      });

      const assistantMessage = {
        id: Date.now() + 1,
        role: 'assistant',
        content: response.data.response,
        timestamp: new Date(),
        functionCalled: response.data.functionCalled
      };

      setMessages(prev => [...prev, assistantMessage]);
      
    } catch (error) {
      console.error('Failed to send message:', error);
      message.error('发送消息失败，请重试');
      
      const errorMessage = {
        id: Date.now() + 1,
        role: 'assistant',
        content: '抱歉，我暂时无法回复您的消息，请稍后重试。',
        timestamp: new Date(),
        type: 'error'
      };
      
      setMessages(prev => [...prev, errorMessage]);
    } finally {
      setLoading(false);
    }
  };

  const sendStreamMessage = async (messageText) => {
    const assistantMessageId = Date.now() + 1;
    let streamContent = '';

    // 添加空的助手消息
    const assistantMessage = {
      id: assistantMessageId,
      role: 'assistant',
      content: '',
      timestamp: new Date(),
      streaming: true
    };
    setMessages(prev => [...prev, assistantMessage]);

    try {
      abortControllerRef.current = new AbortController();
      
      const response = await fetch('/api/customer-service/chat/stream', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          sessionId,
          customerId: sessionInfo.customerId,
          message: messageText
        }),
        signal: abortControllerRef.current.signal
      });

      if (!response.ok) {
        throw new Error('Stream request failed');
      }

      const reader = response.body.getReader();
      const decoder = new TextDecoder();

      while (true) {
        const { done, value } = await reader.read();
        
        if (done) break;

        const chunk = decoder.decode(value, { stream: true });
        const lines = chunk.split('\n');

        for (const line of lines) {
          if (line.startsWith('data:')) {
            const data = line.substring(5).trim();
            if (data && data !== '[DONE]') {
              streamContent += data;
              
              // 更新消息内容
              setMessages(prev => prev.map(msg => 
                msg.id === assistantMessageId 
                  ? { ...msg, content: streamContent }
                  : msg
              ));
            }
          }
        }
      }

      // 流式传输完成，移除 streaming 标记
      setMessages(prev => prev.map(msg => 
        msg.id === assistantMessageId 
          ? { ...msg, streaming: false }
          : msg
      ));

    } catch (error) {
      if (error.name === 'AbortError') {
        console.log('Stream aborted by user');
        message.info('已停止生成');
      } else {
        console.error('Stream failed:', error);
        message.error('流式传输失败');
        
        setMessages(prev => prev.map(msg => 
          msg.id === assistantMessageId 
            ? { 
                ...msg, 
                content: streamContent || '抱歉，消息传输失败，请重试。',
                streaming: false,
                type: 'error'
              }
            : msg
        ));
      }
    } finally {
      setLoading(false);
      abortControllerRef.current = null;
    }
  };

  const stopGeneration = () => {
    if (abortControllerRef.current) {
      abortControllerRef.current.abort();
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  const resetSession = () => {
    setMessages([]);
    setSessionId(null);
    setSessionInfo(null);
    initializeSession();
  };

  const renderMessage = (msg) => {
    const isUser = msg.role === 'user';
    
    return (
      <div key={msg.id} className={`message-item ${isUser ? 'message-user' : 'message-assistant'}`}>
        <div className={`message-bubble ${isUser ? 'user' : 'assistant'}`}>
          <div style={{ display: 'flex', alignItems: 'flex-start', gap: '8px' }}>
            {!isUser && (
              <RobotOutlined style={{ 
                fontSize: '16px', 
                color: msg.type === 'error' ? '#ff4d4f' : '#1890ff',
                marginTop: '2px'
              }} />
            )}
            {isUser && (
              <UserOutlined style={{ 
                fontSize: '16px', 
                color: 'white',
                marginTop: '2px'
              }} />
            )}
            <div style={{ flex: 1 }}>
              <div style={{ whiteSpace: 'pre-wrap', lineHeight: '1.5' }}>
                {msg.content}
                {msg.streaming && (
                  <span className="typing-cursor">▋</span>
                )}
              </div>
              {msg.functionCalled && (
                <div className="function-call-info">
                  <InfoCircleOutlined /> 调用了功能: {msg.functionCalled}
                </div>
              )}
            </div>
          </div>
        </div>
        <div className="message-time">
          {moment(msg.timestamp).format('HH:mm:ss')}
          {msg.streaming && <Tag color="processing" style={{ marginLeft: 8 }}>生成中...</Tag>}
        </div>
      </div>
    );
  };

  const showConfigModal = () => {
    setConfigModalVisible(true);
  };

  const handleConfigSave = (values) => {
    setAiConfig(values);
    setConfigModalVisible(false);
    message.success('AI 配置已更新');
  };

  return (
    <div className="chat-page">
      {/* 会话信息和配置 */}
      {sessionInfo && (
        <div className="session-info">
          <Space split={<Divider type="vertical" />} wrap>
            <span><strong>会话ID:</strong> {sessionInfo.sessionId.substring(0, 8)}...</span>
            <span><strong>用户:</strong> {sessionInfo.customerName}</span>
            <span><strong>开始时间:</strong> {moment(sessionInfo.startTime).format('YYYY-MM-DD HH:mm:ss')}</span>
            <Space>
              <ThunderboltOutlined style={{ color: streamEnabled ? '#52c41a' : '#999' }} />
              <span>流式输出:</span>
              <Switch 
                checked={streamEnabled} 
                onChange={setStreamEnabled}
                size="small"
              />
            </Space>
            <Button 
              size="small" 
              icon={<SettingOutlined />} 
              onClick={showConfigModal}
              type="link"
            >
              AI 配置
            </Button>
            <Button 
              size="small" 
              icon={<ReloadOutlined />} 
              onClick={resetSession}
              type="link"
            >
              重新开始
            </Button>
          </Space>
        </div>
      )}

      {/* 聊天消息区域 */}
      <div className="chat-messages">
        {messages.map(renderMessage)}
        
        {loading && (
          <div className="typing-indicator">
            <RobotOutlined />
            <span>AI 正在思考中</span>
            <div className="typing-dots">
              <div className="typing-dot"></div>
              <div className="typing-dot"></div>
              <div className="typing-dot"></div>
            </div>
          </div>
        )}
        
        <div ref={messagesEndRef} />
      </div>

      {/* 快捷问题 */}
      <div style={{ padding: '16px', background: '#fafafa', borderTop: '1px solid #f0f0f0' }}>
        <div style={{ marginBottom: '12px', color: '#666', fontSize: '14px' }}>
          💡 快捷问题：
        </div>
        <div className="quick-actions">
          {quickQuestions.map((question, index) => (
            <Button
              key={index}
              size="small"
              className="quick-action-btn"
              onClick={() => sendMessage(question)}
              disabled={loading}
            >
              {question}
            </Button>
          ))}
        </div>
      </div>

      {/* 输入区域 */}
      <div className="chat-input-area">
        <Space.Compact style={{ width: '100%' }}>
          <TextArea
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder="请输入您的问题... (Enter 发送，Shift+Enter 换行)"
            autoSize={{ minRows: 1, maxRows: 4 }}
            disabled={loading}
            style={{ resize: 'none' }}
          />
          {loading && streamEnabled ? (
            <Button
              danger
              icon={<StopOutlined />}
              onClick={stopGeneration}
              style={{ height: 'auto' }}
            >
              停止
            </Button>
          ) : (
            <Button
              type="primary"
              icon={<SendOutlined />}
              onClick={() => sendMessage()}
              loading={loading}
              disabled={!inputValue.trim() || !sessionId}
              style={{ height: 'auto' }}
            >
              发送
            </Button>
          )}
        </Space.Compact>
      </div>

      {/* AI 配置弹窗 */}
      <Modal
        title="AI 配置"
        open={configModalVisible}
        onCancel={() => setConfigModalVisible(false)}
        footer={null}
      >
        <Form
          layout="vertical"
          initialValues={aiConfig}
          onFinish={handleConfigSave}
        >
          <Form.Item
            name="model"
            label="模型选择"
            rules={[{ required: true }]}
          >
            <Select>
              <Option value="gpt-3.5-turbo">GPT-3.5 Turbo</Option>
              <Option value="gpt-4">GPT-4</Option>
              <Option value="gpt-4-turbo">GPT-4 Turbo</Option>
            </Select>
          </Form.Item>

          <Form.Item
            name="temperature"
            label="Temperature (创造性)"
            tooltip="值越高，回复越有创造性；值越低，回复越确定"
            rules={[{ required: true }]}
          >
            <InputNumber
              min={0}
              max={2}
              step={0.1}
              style={{ width: '100%' }}
            />
          </Form.Item>

          <Form.Item
            name="maxTokens"
            label="最大 Token 数"
            tooltip="控制回复的最大长度"
            rules={[{ required: true }]}
          >
            <InputNumber
              min={100}
              max={4000}
              step={100}
              style={{ width: '100%' }}
            />
          </Form.Item>

          <Form.Item>
            <Space style={{ width: '100%', justifyContent: 'flex-end' }}>
              <Button onClick={() => setConfigModalVisible(false)}>
                取消
              </Button>
              <Button type="primary" htmlType="submit">
                保存
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ChatPage;