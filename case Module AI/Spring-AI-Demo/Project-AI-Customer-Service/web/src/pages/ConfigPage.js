import React, { useState, useEffect } from 'react';
import {
  Card,
  List,
  Button,
  Space,
  Tag,
  Modal,
  Form,
  Input,
  InputNumber,
  Switch,
  message,
  Popconfirm,
  Empty,
  Spin,
  Tabs,
  Divider,
  Row,
  Col
} from 'antd';
import {
  PlusOutlined,
  EditOutlined,
  DeleteOutlined,
  CopyOutlined,
  CheckCircleOutlined,
  SettingOutlined,
  ReloadOutlined,
  SaveOutlined
} from '@ant-design/icons';
import axios from 'axios';
import moment from 'moment';

const { TextArea } = Input;
const { TabPane } = Tabs;

const ConfigPage = () => {
  const [configurations, setConfigurations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [createModalVisible, setCreateModalVisible] = useState(false);
  const [editModalVisible, setEditModalVisible] = useState(false);
  const [currentConfig, setCurrentConfig] = useState(null);
  const [saving, setSaving] = useState(false);
  
  const [createForm] = Form.useForm();
  const [editForm] = Form.useForm();

  useEffect(() => {
    loadConfigurations();
  }, []);

  const loadConfigurations = async () => {
    setLoading(true);
    try {
      const response = await axios.get('/api/config');
      setConfigurations(response.data.configurations || []);
    } catch (error) {
      console.error('Failed to load configurations:', error);
      message.error('加载配置失败');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateConfig = async (values) => {
    try {
      const response = await axios.post('/api/config', {
        name: values.name,
        description: values.description || ''
      });
      
      message.success('配置创建成功');
      setCreateModalVisible(false);
      createForm.resetFields();
      
      // 创建成功后立即打开编辑窗口
      const newConfig = response.data.config;
      await loadConfigurations();
      handleEditConfig(newConfig);
      
    } catch (error) {
      console.error('Failed to create configuration:', error);
      message.error('创建配置失败');
    }
  };

  const handleEditConfig = (config) => {
    setCurrentConfig(config);
    
    // 设置表单初始值
    editForm.setFieldsValue({
      name: config.name,
      description: config.description,
      chatBaseUrl: config.chatApi?.baseUrl,
      chatModel: config.chatApi?.model,
      chatTemperature: config.chatApi?.temperature,
      chatMaxTokens: config.chatApi?.maxTokens,
      chatApiKeys: config.chatApi?.apiKeys?.join('\n') || '',
      embeddingsBaseUrl: config.embeddingsApi?.baseUrl,
      embeddingsModel: config.embeddingsApi?.model,
      embeddingsApiKeys: config.embeddingsApi?.apiKeys?.join('\n') || '',
      systemPrompt: config.systemPrompt,
      enableFunctionCalling: config.otherConfig?.enableFunctionCalling,
      enableStreaming: config.otherConfig?.enableStreaming,
      sessionTimeout: config.otherConfig?.sessionTimeout,
      maxHistoryMessages: config.otherConfig?.maxHistoryMessages
    });
    
    setEditModalVisible(true);
  };

  const handleUpdateConfig = async (values) => {
    setSaving(true);
    try {
      const updatedConfig = {
        id: currentConfig.id,
        name: values.name,
        description: values.description,
        active: currentConfig.active,
        createdAt: currentConfig.createdAt,
        updatedAt: new Date().toISOString(),
        chatApi: {
          baseUrl: values.chatBaseUrl,
          model: values.chatModel,
          temperature: values.chatTemperature,
          maxTokens: values.chatMaxTokens,
          apiKeys: values.chatApiKeys ? values.chatApiKeys.split('\n').filter(k => k.trim()) : []
        },
        embeddingsApi: {
          baseUrl: values.embeddingsBaseUrl,
          model: values.embeddingsModel,
          apiKeys: values.embeddingsApiKeys ? values.embeddingsApiKeys.split('\n').filter(k => k.trim()) : []
        },
        systemPrompt: values.systemPrompt,
        otherConfig: {
          enableFunctionCalling: values.enableFunctionCalling,
          enableStreaming: values.enableStreaming,
          sessionTimeout: values.sessionTimeout,
          maxHistoryMessages: values.maxHistoryMessages
        }
      };

      await axios.put(`/api/config/${currentConfig.id}`, updatedConfig);
      message.success('配置更新成功');
      setEditModalVisible(false);
      loadConfigurations();
    } catch (error) {
      console.error('Failed to update configuration:', error);
      message.error('更新配置失败');
    } finally {
      setSaving(false);
    }
  };

  const handleActivateConfig = async (id) => {
    try {
      await axios.post(`/api/config/${id}/activate`);
      message.success('配置已激活');
      loadConfigurations();
    } catch (error) {
      console.error('Failed to activate configuration:', error);
      message.error('激活配置失败');
    }
  };

  const handleDeleteConfig = async (id) => {
    try {
      const response = await axios.delete(`/api/config/${id}`);
      if (response.data.success) {
        message.success('配置删除成功');
        loadConfigurations();
      } else {
        message.warning(response.data.message);
      }
    } catch (error) {
      console.error('Failed to delete configuration:', error);
      message.error('删除配置失败');
    }
  };

  const handleDuplicateConfig = async (id, name) => {
    try {
      await axios.post(`/api/config/${id}/duplicate`, { name: name + ' (副本)' });
      message.success('配置复制成功');
      loadConfigurations();
    } catch (error) {
      console.error('Failed to duplicate configuration:', error);
      message.error('复制配置失败');
    }
  };

  if (loading) {
    return (
      <div style={{ textAlign: 'center', padding: '100px' }}>
        <Spin size="large" />
        <div style={{ marginTop: 16 }}>加载配置中...</div>
      </div>
    );
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h2 style={{ margin: 0 }}>⚙️ API 配置管理</h2>
            <p style={{ margin: '4px 0 0 0', color: '#666' }}>
              管理多个 API 配置，随时切换使用
            </p>
          </div>
          <Space>
            <Button icon={<ReloadOutlined />} onClick={loadConfigurations}>
              刷新
            </Button>
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={() => setCreateModalVisible(true)}
            >
              新建配置
            </Button>
          </Space>
        </div>
      </div>

      <Card style={{ background: '#fafafa' }}>
        <List
          dataSource={configurations}
          split={false}
          locale={{
            emptyText: (
              <Empty description="暂无配置，请创建一个新配置">
                <Button type="primary" icon={<PlusOutlined />} onClick={() => setCreateModalVisible(true)}>
                  创建配置
                </Button>
              </Empty>
            )
          }}
          renderItem={(config) => (
            <List.Item
              style={{
                background: config.active ? '#f6ffed' : 'white',
                border: config.active ? '2px solid #52c41a' : '1px solid #f0f0f0',
                borderRadius: '8px',
                marginBottom: '16px',
                padding: '16px'
              }}
            >
              <div style={{ width: '100%' }}>
                {/* 头部：名称和操作按钮 */}
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
                  <Space size="large">
                    <SettingOutlined style={{ fontSize: 32, color: config.active ? '#52c41a' : '#1890ff' }} />
                    <div>
                      <div>
                        <Space>
                          <span style={{ fontSize: 18, fontWeight: 'bold' }}>{config.name}</span>
                          {config.active && (
                            <Tag color="success" icon={<CheckCircleOutlined />}>
                              当前使用
                            </Tag>
                          )}
                        </Space>
                      </div>
                      <div style={{ color: '#666', marginTop: '4px' }}>{config.description || '暂无描述'}</div>
                    </div>
                  </Space>
                  
                  <Space>
                    {!config.active && (
                      <Button
                        type="primary"
                        size="small"
                        onClick={() => handleActivateConfig(config.id)}
                      >
                        激活
                      </Button>
                    )}
                    <Button
                      size="small"
                      icon={<EditOutlined />}
                      onClick={() => handleEditConfig(config)}
                    >
                      编辑
                    </Button>
                    <Button
                      size="small"
                      icon={<CopyOutlined />}
                      onClick={() => handleDuplicateConfig(config.id, config.name)}
                    >
                      复制
                    </Button>
                    <Popconfirm
                      title="确定要删除这个配置吗？"
                      description="删除后无法恢复"
                      onConfirm={() => handleDeleteConfig(config.id)}
                      okText="确定"
                      cancelText="取消"
                      disabled={config.active}
                    >
                      <Button
                        size="small"
                        danger
                        icon={<DeleteOutlined />}
                        disabled={config.active}
                      >
                        删除
                      </Button>
                    </Popconfirm>
                  </Space>
                </div>

                <Divider style={{ margin: '12px 0' }} />

                {/* 详细配置信息 */}
                <Row gutter={[16, 8]}>
                  <Col span={12}>
                    <div style={{ fontSize: '12px', color: '#999' }}>Chat API</div>
                    <div style={{ marginTop: '4px' }}>
                      <div><strong>Base URL:</strong> {config.chatApi?.baseUrl || 'N/A'}</div>
                      <div><strong>模型:</strong> {config.chatApi?.model || 'N/A'}</div>
                      <div>
                        <strong>API Keys:</strong> {config.chatApi?.apiKeys?.length || 0} 个
                        {config.chatApi?.apiKeys?.length > 0 && (
                          <span style={{ marginLeft: '8px', color: '#52c41a' }}>✓</span>
                        )}
                      </div>
                      <div>
                        <strong>Temperature:</strong> {config.chatApi?.temperature || 'N/A'} | 
                        <strong> Max Tokens:</strong> {config.chatApi?.maxTokens || 'N/A'}
                      </div>
                    </div>
                  </Col>

                  <Col span={12}>
                    <div style={{ fontSize: '12px', color: '#999' }}>Embeddings API</div>
                    <div style={{ marginTop: '4px' }}>
                      <div><strong>Base URL:</strong> {config.embeddingsApi?.baseUrl || 'N/A'}</div>
                      <div><strong>模型:</strong> {config.embeddingsApi?.model || 'N/A'}</div>
                      <div>
                        <strong>API Keys:</strong> {config.embeddingsApi?.apiKeys?.length || 0} 个
                        {config.embeddingsApi?.apiKeys?.length > 0 && (
                          <span style={{ marginLeft: '8px', color: '#52c41a' }}>✓</span>
                        )}
                      </div>
                    </div>
                  </Col>

                  <Col span={12}>
                    <div style={{ fontSize: '12px', color: '#999' }}>功能配置</div>
                    <div style={{ marginTop: '4px' }}>
                      <Space split="|">
                        <span>
                          Function Calling: {config.otherConfig?.enableFunctionCalling ? 
                            <Tag color="success" style={{ marginLeft: 4 }}>启用</Tag> : 
                            <Tag style={{ marginLeft: 4 }}>禁用</Tag>
                          }
                        </span>
                        <span>
                          Streaming: {config.otherConfig?.enableStreaming ? 
                            <Tag color="success" style={{ marginLeft: 4 }}>启用</Tag> : 
                            <Tag style={{ marginLeft: 4 }}>禁用</Tag>
                          }
                        </span>
                      </Space>
                      <div style={{ marginTop: '4px' }}>
                        <strong>会话超时:</strong> {config.otherConfig?.sessionTimeout || 'N/A'}秒 | 
                        <strong> 历史消息:</strong> {config.otherConfig?.maxHistoryMessages || 'N/A'}条
                      </div>
                    </div>
                  </Col>

                  <Col span={12}>
                    <div style={{ fontSize: '12px', color: '#999' }}>时间信息</div>
                    <div style={{ marginTop: '4px' }}>
                      <div><strong>创建时间:</strong> {config.createdAt ? moment(config.createdAt).format('YYYY-MM-DD HH:mm:ss') : 'N/A'}</div>
                      <div><strong>更新时间:</strong> {config.updatedAt ? moment(config.updatedAt).format('YYYY-MM-DD HH:mm:ss') : 'N/A'}</div>
                      <div><strong>配置 ID:</strong> {config.id ? config.id.substring(0, 8) + '...' : 'N/A'}</div>
                    </div>
                  </Col>
                </Row>

                {/* 系统提示词预览 */}
                {config.systemPrompt && config.systemPrompt.length > 0 && (
                  <>
                    <Divider style={{ margin: '12px 0' }} />
                    <div>
                      <div style={{ fontSize: '12px', color: '#999', marginBottom: '4px' }}>系统提示词</div>
                      <div style={{ 
                        background: '#fafafa', 
                        padding: '8px 12px', 
                        borderRadius: '4px',
                        fontSize: '12px',
                        maxHeight: '60px',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'pre-wrap'
                      }}>
                        {(config.systemPrompt || '').substring(0, 150)}
                        {(config.systemPrompt || '').length > 150 && '...'}
                      </div>
                    </div>
                  </>
                )}
              </div>
            </List.Item>
          )}
        />
      </Card>

      {/* 创建配置弹窗 */}
      <Modal
        title="创建新配置"
        open={createModalVisible}
        onCancel={() => {
          setCreateModalVisible(false);
          createForm.resetFields();
        }}
        footer={null}
      >
        <Form form={createForm} layout="vertical" onFinish={handleCreateConfig}>
          <Form.Item
            name="name"
            label="配置名称"
            rules={[{ required: true, message: '请输入配置名称' }]}
          >
            <Input placeholder="例如：开发环境、生产环境" />
          </Form.Item>
          <Form.Item name="description" label="配置描述">
            <TextArea rows={3} placeholder="描述这个配置的用途" />
          </Form.Item>
          <Form.Item>
            <Space style={{ width: '100%', justifyContent: 'flex-end' }}>
              <Button onClick={() => setCreateModalVisible(false)}>取消</Button>
              <Button type="primary" htmlType="submit">
                创建并配置
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>

      {/* 编辑配置弹窗 */}
      <Modal
        title={`编辑配置: ${currentConfig?.name}`}
        open={editModalVisible}
        onCancel={() => setEditModalVisible(false)}
        footer={null}
        width={900}
        style={{ top: 20 }}
      >
        <Form
          form={editForm}
          layout="vertical"
          onFinish={handleUpdateConfig}
        >
          <Tabs defaultActiveKey="1">
            {/* 基本信息 */}
            <TabPane tab="基本信息" key="1">
              <Form.Item
                name="name"
                label="配置名称"
                rules={[{ required: true }]}
              >
                <Input />
              </Form.Item>
              <Form.Item name="description" label="配置描述">
                <TextArea rows={3} />
              </Form.Item>
            </TabPane>

            {/* Chat API */}
            <TabPane tab="Chat API" key="2">
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item
                    name="chatBaseUrl"
                    label="Base URL"
                    rules={[{ required: true }]}
                  >
                    <Input placeholder="https://api.siliconflow.cn/v1" />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item
                    name="chatModel"
                    label="模型"
                    rules={[{ required: true }]}
                  >
                    <Input placeholder="Qwen/Qwen2.5-7B-Instruct" />
                  </Form.Item>
                </Col>
              </Row>

              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item
                    name="chatTemperature"
                    label="Temperature"
                    rules={[{ required: true }]}
                  >
                    <InputNumber min={0} max={2} step={0.1} style={{ width: '100%' }} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item
                    name="chatMaxTokens"
                    label="Max Tokens"
                    rules={[{ required: true }]}
                  >
                    <InputNumber min={100} max={4000} step={100} style={{ width: '100%' }} />
                  </Form.Item>
                </Col>
              </Row>

              <Form.Item
                name="chatApiKeys"
                label="API Keys (每行一个)"
                rules={[{ required: true, message: '请输入至少一个 API Key' }]}
              >
                <TextArea
                  rows={6}
                  placeholder="sk-xxxxxxxxxxxxxxxxxxxxxxxx&#10;sk-yyyyyyyyyyyyyyyyyyyyyyyy"
                />
              </Form.Item>
            </TabPane>

            {/* Embeddings API */}
            <TabPane tab="Embeddings API" key="3">
              <Form.Item
                name="embeddingsBaseUrl"
                label="Base URL"
                rules={[{ required: true }]}
              >
                <Input placeholder="https://api.siliconflow.cn/v1" />
              </Form.Item>

              <Form.Item
                name="embeddingsModel"
                label="模型"
                rules={[{ required: true }]}
              >
                <Input placeholder="BAAI/bge-large-zh-v1.5" />
              </Form.Item>

              <Form.Item
                name="embeddingsApiKeys"
                label="API Keys (每行一个)"
                rules={[{ required: true, message: '请输入至少一个 API Key' }]}
              >
                <TextArea
                  rows={6}
                  placeholder="sk-xxxxxxxxxxxxxxxxxxxxxxxx&#10;sk-yyyyyyyyyyyyyyyyyyyyyyyy"
                />
              </Form.Item>
            </TabPane>

            {/* 系统提示词 */}
            <TabPane tab="系统提示词" key="4">
              <Form.Item
                name="systemPrompt"
                label="系统提示词"
                rules={[{ required: true }]}
              >
                <TextArea rows={15} />
              </Form.Item>
            </TabPane>

            {/* 其他配置 */}
            <TabPane tab="其他配置" key="5">
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item
                    name="enableFunctionCalling"
                    label="启用 Function Calling"
                    valuePropName="checked"
                  >
                    <Switch />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item
                    name="enableStreaming"
                    label="启用流式输出"
                    valuePropName="checked"
                  >
                    <Switch />
                  </Form.Item>
                </Col>
              </Row>

              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item name="sessionTimeout" label="会话超时 (秒)">
                    <InputNumber min={60} max={7200} style={{ width: '100%' }} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item name="maxHistoryMessages" label="最大历史消息数">
                    <InputNumber min={1} max={50} style={{ width: '100%' }} />
                  </Form.Item>
                </Col>
              </Row>
            </TabPane>
          </Tabs>

          <Divider />

          <Form.Item>
            <Space style={{ width: '100%', justifyContent: 'flex-end' }}>
              <Button onClick={() => setEditModalVisible(false)}>
                取消
              </Button>
              <Button
                type="primary"
                htmlType="submit"
                icon={<SaveOutlined />}
                loading={saving}
              >
                保存配置
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ConfigPage;
