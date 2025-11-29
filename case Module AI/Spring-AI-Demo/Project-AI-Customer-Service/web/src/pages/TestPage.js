import React, { useState } from 'react';
import { 
  Card, 
  Form, 
  Input, 
  Button, 
  Space, 
  message, 
  Divider,
  Row,
  Col,
  InputNumber,
  Alert,
  Tag,
  Statistic,
  Progress
} from 'antd';
import { 
  SendOutlined, 
  ThunderboltOutlined, 
  BugOutlined,
  ReloadOutlined,
  ApiOutlined,
  ClockCircleOutlined,
  CheckCircleOutlined,
  ExclamationCircleOutlined
} from '@ant-design/icons';
import axios from 'axios';
import moment from 'moment';

const { TextArea } = Input;

const TestPage = () => {
  const [singleForm] = Form.useForm();
  const [concurrentForm] = Form.useForm();
  const [errorForm] = Form.useForm();
  
  const [singleLoading, setSingleLoading] = useState(false);
  const [concurrentLoading, setConcurrentLoading] = useState(false);
  const [errorLoading, setErrorLoading] = useState(false);
  
  const [singleResult, setSingleResult] = useState(null);
  const [concurrentResult, setConcurrentResult] = useState(null);
  const [errorResult, setErrorResult] = useState(null);

  // 单次测试
  const handleSingleTest = async (values) => {
    setSingleLoading(true);
    setSingleResult(null);
    
    try {
      const response = await axios.post('/api/test/single', {
        message: values.message
      });
      
      setSingleResult(response.data);
      
      if (response.data.success) {
        message.success('单次测试完成');
      } else {
        message.error('测试失败: ' + response.data.error);
      }
    } catch (error) {
      console.error('Single test failed:', error);
      message.error('测试请求失败');
      setSingleResult({
        success: false,
        error: error.message,
        timestamp: new Date()
      });
    } finally {
      setSingleLoading(false);
    }
  };

  // 并发测试
  const handleConcurrentTest = async (values) => {
    setConcurrentLoading(true);
    setConcurrentResult(null);
    
    try {
      const response = await axios.post('/api/test/concurrent', {
        message: values.message,
        concurrency: values.concurrency,
        requestsPerTask: values.requestsPerTask
      });
      
      setConcurrentResult(response.data);
      
      if (response.data.success) {
        message.success('并发测试完成');
      } else {
        message.error('测试失败: ' + response.data.error);
      }
    } catch (error) {
      console.error('Concurrent test failed:', error);
      message.error('测试请求失败');
      setConcurrentResult({
        success: false,
        error: error.message,
        timestamp: new Date()
      });
    } finally {
      setConcurrentLoading(false);
    }
  };

  // 模拟错误
  const handleErrorSimulation = async (values) => {
    setErrorLoading(true);
    setErrorResult(null);
    
    try {
      const response = await axios.post('/api/test/simulate-error', {
        apiKey: values.apiKey,
        errorMessage: values.errorMessage
      });
      
      setErrorResult(response.data);
      
      if (response.data.success) {
        message.success('错误模拟完成');
      } else {
        message.error('模拟失败: ' + response.data.error);
      }
    } catch (error) {
      console.error('Error simulation failed:', error);
      message.error('模拟请求失败');
      setErrorResult({
        success: false,
        error: error.message,
        timestamp: new Date()
      });
    } finally {
      setErrorLoading(false);
    }
  };

  // 重置统计
  const handleResetStats = async () => {
    try {
      await axios.post('/api/test/reset-stats');
      message.success('统计数据已重置');
      setSingleResult(null);
      setConcurrentResult(null);
      setErrorResult(null);
    } catch (error) {
      console.error('Reset stats failed:', error);
      message.error('重置失败');
    }
  };

  // 快速测试按钮
  const quickTests = [
    { label: '简单问候', message: '你好' },
    { label: '订单查询', message: '查询订单 ORD001' },
    { label: '物流跟踪', message: '跟踪包裹 TRK123456' },
    { label: '退款申请', message: '我想申请退款' },
    { label: '投诉建议', message: '我要投诉一个问题' }
  ];

  const renderResult = (result, title) => {
    if (!result) return null;

    return (
      <Card 
        title={title} 
        size="small" 
        style={{ marginTop: 16 }}
        extra={
          <Tag color={result.success ? 'green' : 'red'}>
            {result.success ? '成功' : '失败'}
          </Tag>
        }
      >
        {result.success ? (
          <Space direction="vertical" style={{ width: '100%' }}>
            {result.response && (
              <Alert
                message="响应内容"
                description={result.response}
                type="success"
                showIcon
              />
            )}
            
            <Row gutter={16}>
              {result.duration && (
                <Col span={8}>
                  <Statistic
                    title="响应时间"
                    value={result.duration}
                    suffix="ms"
                    prefix={<ClockCircleOutlined />}
                  />
                </Col>
              )}
              
              {result.totalRequests && (
                <Col span={8}>
                  <Statistic
                    title="总请求数"
                    value={result.totalRequests}
                    prefix={<ApiOutlined />}
                  />
                </Col>
              )}
              
              {result.requestsPerSecond && (
                <Col span={8}>
                  <Statistic
                    title="QPS"
                    value={result.requestsPerSecond.toFixed(2)}
                    suffix="/s"
                    prefix={<ThunderboltOutlined />}
                  />
                </Col>
              )}
            </Row>
            
            {result.concurrency && (
              <div>
                <strong>并发配置:</strong> {result.concurrency} 个并发任务
              </div>
            )}
          </Space>
        ) : (
          <Alert
            message="测试失败"
            description={result.error}
            type="error"
            showIcon
          />
        )}
        
        <div style={{ marginTop: 12, fontSize: '12px', color: '#666' }}>
          测试时间: {moment(result.timestamp).format('YYYY-MM-DD HH:mm:ss')}
        </div>
      </Card>
    );
  };

  return (
    <div className="page-container">
      {/* 页面头部 */}
      <div className="page-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h2 style={{ margin: 0 }}>🧪 API 测试工具</h2>
            <p style={{ margin: '4px 0 0 0', color: '#666' }}>
              测试 API Key 轮询、负载均衡和故障切换功能
            </p>
          </div>
          <Button
            icon={<ReloadOutlined />}
            onClick={handleResetStats}
            danger
          >
            重置统计
          </Button>
        </div>
      </div>

      <Row gutter={[24, 24]}>
        {/* 单次测试 */}
        <Col xs={24} lg={12}>
          <Card 
            title={
              <Space>
                <SendOutlined />
                单次 API 测试
              </Space>
            }
          >
            <div className="api-endpoint-info">
              POST /api/test/single
            </div>
            
            {/* 快速测试按钮 */}
            <div style={{ marginBottom: 16 }}>
              <div style={{ marginBottom: 8, fontSize: '14px', fontWeight: 'bold' }}>
                快速测试:
              </div>
              <div className="quick-test-buttons">
                {quickTests.map((test, index) => (
                  <Button
                    key={index}
                    size="small"
                    onClick={() => {
                      singleForm.setFieldsValue({ message: test.message });
                      handleSingleTest({ message: test.message });
                    }}
                    loading={singleLoading}
                  >
                    {test.label}
                  </Button>
                ))}
              </div>
            </div>
            
            <Form
              form={singleForm}
              layout="vertical"
              onFinish={handleSingleTest}
              initialValues={{ message: '你好，我想查询订单状态' }}
            >
              <Form.Item
                name="message"
                label="测试消息"
                rules={[{ required: true, message: '请输入测试消息' }]}
              >
                <TextArea
                  rows={3}
                  placeholder="输入要测试的消息内容"
                />
              </Form.Item>
              
              <Form.Item>
                <Button
                  type="primary"
                  htmlType="submit"
                  loading={singleLoading}
                  icon={<SendOutlined />}
                  block
                >
                  发送测试
                </Button>
              </Form.Item>
            </Form>
            
            {renderResult(singleResult, '单次测试结果')}
          </Card>
        </Col>

        {/* 并发测试 */}
        <Col xs={24} lg={12}>
          <Card 
            title={
              <Space>
                <ThunderboltOutlined />
                并发压力测试
              </Space>
            }
          >
            <div className="api-endpoint-info">
              POST /api/test/concurrent
            </div>
            
            <Form
              form={concurrentForm}
              layout="vertical"
              onFinish={handleConcurrentTest}
              initialValues={{ 
                message: '并发测试消息',
                concurrency: 5,
                requestsPerTask: 3
              }}
            >
              <Form.Item
                name="message"
                label="测试消息"
                rules={[{ required: true, message: '请输入测试消息' }]}
              >
                <Input placeholder="输入要测试的消息内容" />
              </Form.Item>
              
              <div className="concurrent-test-config">
                <Form.Item
                  name="concurrency"
                  label="并发数"
                  rules={[{ required: true, message: '请输入并发数' }]}
                >
                  <InputNumber
                    min={1}
                    max={20}
                    style={{ width: '100%' }}
                    placeholder="并发任务数"
                  />
                </Form.Item>
                
                <Form.Item
                  name="requestsPerTask"
                  label="每任务请求数"
                  rules={[{ required: true, message: '请输入每任务请求数' }]}
                >
                  <InputNumber
                    min={1}
                    max={10}
                    style={{ width: '100%' }}
                    placeholder="每个任务的请求数"
                  />
                </Form.Item>
              </div>
              
              <Form.Item>
                <Button
                  type="primary"
                  htmlType="submit"
                  loading={concurrentLoading}
                  icon={<ThunderboltOutlined />}
                  block
                  danger
                >
                  开始压力测试
                </Button>
              </Form.Item>
            </Form>
            
            {renderResult(concurrentResult, '并发测试结果')}
          </Card>
        </Col>

        {/* 错误模拟 */}
        <Col xs={24}>
          <Card 
            title={
              <Space>
                <BugOutlined />
                错误模拟测试
              </Space>
            }
          >
            <div className="api-endpoint-info">
              POST /api/test/simulate-error
            </div>
            
            <Alert
              message="注意"
              description="此功能用于模拟 API Key 错误，测试系统的故障切换能力。请谨慎使用。"
              type="warning"
              showIcon
              style={{ marginBottom: 16 }}
            />
            
            <Form
              form={errorForm}
              layout="vertical"
              onFinish={handleErrorSimulation}
              initialValues={{ 
                apiKey: 'sk-test-key-for-simulation',
                errorMessage: '模拟的 API 错误'
              }}
            >
              <Row gutter={16}>
                <Col xs={24} md={12}>
                  <Form.Item
                    name="apiKey"
                    label="API Key"
                    rules={[{ required: true, message: '请输入 API Key' }]}
                  >
                    <Input placeholder="输入要模拟错误的 API Key" />
                  </Form.Item>
                </Col>
                
                <Col xs={24} md={12}>
                  <Form.Item
                    name="errorMessage"
                    label="错误消息"
                    rules={[{ required: true, message: '请输入错误消息' }]}
                  >
                    <Input placeholder="输入错误消息内容" />
                  </Form.Item>
                </Col>
              </Row>
              
              <Form.Item>
                <Button
                  type="primary"
                  htmlType="submit"
                  loading={errorLoading}
                  icon={<BugOutlined />}
                  danger
                >
                  模拟错误
                </Button>
              </Form.Item>
            </Form>
            
            {renderResult(errorResult, '错误模拟结果')}
          </Card>
        </Col>
      </Row>

      {/* 测试说明 */}
      <Card title="测试说明" style={{ marginTop: 24 }}>
        <Row gutter={[16, 16]}>
          <Col xs={24} md={8}>
            <Card size="small" title="单次测试">
              <p>测试单个 API 请求的响应时间和成功率，验证基本的 API Key 轮询功能。</p>
              <ul>
                <li>测试消息发送</li>
                <li>响应时间统计</li>
                <li>错误处理验证</li>
              </ul>
            </Card>
          </Col>
          
          <Col xs={24} md={8}>
            <Card size="small" title="并发测试">
              <p>测试系统在高并发情况下的表现，验证负载均衡和性能。</p>
              <ul>
                <li>多任务并发执行</li>
                <li>QPS 性能测试</li>
                <li>系统稳定性验证</li>
              </ul>
            </Card>
          </Col>
          
          <Col xs={24} md={8}>
            <Card size="small" title="错误模拟">
              <p>模拟 API Key 错误，测试系统的故障切换和恢复能力。</p>
              <ul>
                <li>错误记录功能</li>
                <li>自动切换验证</li>
                <li>健康状态管理</li>
              </ul>
            </Card>
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default TestPage;