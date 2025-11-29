import React, { useState, useEffect } from 'react';
import { 
  Card, 
  Row, 
  Col, 
  Statistic, 
  Button, 
  Tag, 
  Space, 
  message, 
  Spin,
  Progress,
  Tooltip,
  Alert,
  Divider
} from 'antd';
import { 
  ReloadOutlined, 
  SwapOutlined, 
  CheckCircleOutlined, 
  ExclamationCircleOutlined,
  ApiOutlined,
  BarChartOutlined,
  ClockCircleOutlined
} from '@ant-design/icons';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip as RechartsTooltip, ResponsiveContainer } from 'recharts';
import axios from 'axios';
import moment from 'moment';

const MonitorPage = () => {
  const [dashboardData, setDashboardData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [lastUpdated, setLastUpdated] = useState(null);

  useEffect(() => {
    loadDashboard();
    // 每30秒自动刷新
    const interval = setInterval(loadDashboard, 30000);
    return () => clearInterval(interval);
  }, []);

  const loadDashboard = async (showLoading = false) => {
    if (showLoading) setRefreshing(true);
    
    try {
      const response = await axios.get('/api/monitor/dashboard');
      setDashboardData(response.data);
      setLastUpdated(new Date());
    } catch (error) {
      console.error('Failed to load dashboard:', error);
      message.error('加载监控数据失败');
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  const switchChatKey = async () => {
    try {
      await axios.post('/api/monitor/switch/chat');
      message.success('Chat API Key 切换成功');
      loadDashboard();
    } catch (error) {
      console.error('Failed to switch chat key:', error);
      message.error('切换失败');
    }
  };

  const switchEmbeddingsKey = async () => {
    try {
      await axios.post('/api/monitor/switch/embeddings');
      message.success('Embeddings API Key 切换成功');
      loadDashboard();
    } catch (error) {
      console.error('Failed to switch embeddings key:', error);
      message.error('切换失败');
    }
  };

  const renderSummaryCards = () => {
    if (!dashboardData?.summary) return null;

    const { summary } = dashboardData;
    
    return (
      <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="总 Key 数量"
              value={summary.totalKeys}
              prefix={<ApiOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="健康 Key"
              value={summary.healthyKeys}
              prefix={<CheckCircleOutlined />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="异常 Key"
              value={summary.unhealthyKeys}
              prefix={<ExclamationCircleOutlined />}
              valueStyle={{ color: '#ff4d4f' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="今日使用量"
              value={summary.totalUsageToday}
              prefix={<BarChartOutlined />}
              valueStyle={{ color: '#722ed1' }}
            />
          </Card>
        </Col>
      </Row>
    );
  };

  const renderHealthRate = () => {
    if (!dashboardData?.summary) return null;

    const { summary } = dashboardData;
    const healthRate = (summary.healthRate * 100).toFixed(1);
    
    let status = 'success';
    let color = '#52c41a';
    
    if (summary.healthRate < 0.5) {
      status = 'exception';
      color = '#ff4d4f';
    } else if (summary.healthRate < 0.8) {
      status = 'active';
      color = '#faad14';
    }

    return (
      <Card title="系统健康率" style={{ marginBottom: 24 }}>
        <Progress
          type="circle"
          percent={parseFloat(healthRate)}
          status={status}
          strokeColor={color}
          format={percent => `${percent}%`}
        />
        <div style={{ marginTop: 16, textAlign: 'center' }}>
          <Space direction="vertical">
            <span style={{ color: '#666' }}>
              {summary.healthyKeys} / {summary.totalKeys} Keys 正常运行
            </span>
            {summary.unhealthyKeys > 0 && (
              <Alert
                message={`${summary.unhealthyKeys} 个 Key 存在异常，请检查`}
                type="warning"
                showIcon
                size="small"
              />
            )}
          </Space>
        </div>
      </Card>
    );
  };

  const renderKeyCards = () => {
    if (!dashboardData?.keys) return null;

    return Object.entries(dashboardData.keys).map(([keyId, keyData]) => (
      <Col xs={24} lg={12} xl={8} key={keyId}>
        <Card
          title={
            <Space>
              <ApiOutlined />
              <span>{keyData.maskedKey}</span>
              <Tag color={keyData.healthy ? 'green' : 'red'}>
                {keyData.healthy ? '健康' : '异常'}
              </Tag>
            </Space>
          }
          size="small"
          className="key-card"
        >
          <Row gutter={[8, 8]}>
            <Col span={12}>
              <Statistic
                title="今日使用"
                value={keyData.todayUsage}
                valueStyle={{ fontSize: '16px' }}
              />
            </Col>
            <Col span={12}>
              <Statistic
                title="总使用量"
                value={keyData.totalUsage}
                valueStyle={{ fontSize: '16px' }}
              />
            </Col>
            <Col span={12}>
              <Statistic
                title="错误次数"
                value={keyData.errorCount}
                valueStyle={{ 
                  fontSize: '16px',
                  color: keyData.errorCount > 0 ? '#ff4d4f' : '#52c41a'
                }}
              />
            </Col>
            <Col span={12}>
              <Statistic
                title="连续错误"
                value={keyData.consecutiveErrors}
                valueStyle={{ 
                  fontSize: '16px',
                  color: keyData.consecutiveErrors > 0 ? '#ff4d4f' : '#52c41a'
                }}
              />
            </Col>
          </Row>
          
          {keyData.lastErrorMessage && (
            <Alert
              message="最近错误"
              description={keyData.lastErrorMessage}
              type="error"
              size="small"
              style={{ marginTop: 12 }}
            />
          )}
          
          {keyData.lastUsedTime && (
            <div style={{ marginTop: 12, fontSize: '12px', color: '#666' }}>
              <ClockCircleOutlined /> 最后使用: {moment(keyData.lastUsedTime).format('MM-DD HH:mm:ss')}
            </div>
          )}
        </Card>
      </Col>
    ));
  };

  if (loading) {
    return (
      <div className="loading-container">
        <Spin size="large" />
        <div style={{ marginTop: 16 }}>加载监控数据中...</div>
      </div>
    );
  }

  return (
    <div className="page-container">
      {/* 页面头部 */}
      <div className="page-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h2 style={{ margin: 0 }}>🔑 API Key 监控面板</h2>
            <p style={{ margin: '4px 0 0 0', color: '#666' }}>
              实时监控 API Key 使用情况和健康状态
            </p>
          </div>
          <Space>
            <Button
              icon={<ReloadOutlined />}
              onClick={() => loadDashboard(true)}
              loading={refreshing}
            >
              刷新数据
            </Button>
            <Button
              icon={<SwapOutlined />}
              onClick={switchChatKey}
              type="primary"
              ghost
            >
              切换 Chat Key
            </Button>
            <Button
              icon={<SwapOutlined />}
              onClick={switchEmbeddingsKey}
              type="primary"
              ghost
            >
              切换 Embeddings Key
            </Button>
          </Space>
        </div>
        
        {lastUpdated && (
          <div style={{ marginTop: 12, fontSize: '12px', color: '#999' }}>
            最后更新: {moment(lastUpdated).format('YYYY-MM-DD HH:mm:ss')}
          </div>
        )}
      </div>

      {/* 摘要统计 */}
      {renderSummaryCards()}

      <Row gutter={[24, 24]}>
        {/* 健康率 */}
        <Col xs={24} lg={8}>
          {renderHealthRate()}
        </Col>

        {/* 使用趋势图 */}
        <Col xs={24} lg={16}>
          <Card title="使用趋势" style={{ marginBottom: 24 }}>
            <div style={{ height: 200, display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#666' }}>
              📈 使用趋势图 (功能开发中)
            </div>
          </Card>
        </Col>
      </Row>

      {/* API Key 详情卡片 */}
      <Card title="API Key 详情" style={{ marginBottom: 24 }}>
        <Row gutter={[16, 16]}>
          {renderKeyCards()}
        </Row>
        
        {(!dashboardData?.keys || Object.keys(dashboardData.keys).length === 0) && (
          <div style={{ textAlign: 'center', padding: '40px', color: '#666' }}>
            暂无 API Key 使用数据
          </div>
        )}
      </Card>

      {/* 系统信息 */}
      <Card title="系统信息">
        <Row gutter={[16, 16]}>
          <Col xs={24} sm={8}>
            <Statistic
              title="监控开始时间"
              value={moment().subtract(1, 'hour').format('HH:mm:ss')}
              prefix={<ClockCircleOutlined />}
            />
          </Col>
          <Col xs={24} sm={8}>
            <Statistic
              title="自动刷新间隔"
              value="30"
              suffix="秒"
              prefix={<ReloadOutlined />}
            />
          </Col>
          <Col xs={24} sm={8}>
            <Statistic
              title="数据保留时间"
              value="24"
              suffix="小时"
              prefix={<BarChartOutlined />}
            />
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default MonitorPage;