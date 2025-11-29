import React from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { Layout, Menu } from 'antd';
import { 
  MessageOutlined, 
  DashboardOutlined, 
  ApiOutlined,
  SettingOutlined
} from '@ant-design/icons';
import ChatPage from './pages/ChatPage';
import MonitorPage from './pages/MonitorPage';
import TestPage from './pages/TestPage';
import ConfigPage from './pages/ConfigPage';
import ErrorBoundary from './components/ErrorBoundary';
import './App.css';

const { Header, Sider, Content } = Layout;

const AppContent = () => {
  const [collapsed, setCollapsed] = React.useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const menuItems = [
    {
      key: 'chat',
      icon: <MessageOutlined />,
      label: '智能客服',
      path: '/'
    },
    {
      key: 'monitor',
      icon: <DashboardOutlined />,
      label: 'API 监控',
      path: '/monitor'
    },
    {
      key: 'test',
      icon: <ApiOutlined />,
      label: 'API 测试',
      path: '/test'
    },
    {
      key: 'config',
      icon: <SettingOutlined />,
      label: 'API 配置',
      path: '/config'
    }
  ];

  // 根据当前路径确定选中的菜单项
  const getSelectedKey = () => {
    const path = location.pathname;
    if (path === '/') return 'chat';
    if (path === '/monitor') return 'monitor';
    if (path === '/test') return 'test';
    if (path === '/config') return 'config';
    return 'chat';
  };

  const handleMenuClick = (e) => {
    const item = menuItems.find(item => item.key === e.key);
    if (item) {
      navigate(item.path);
    }
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider 
        collapsible 
        collapsed={collapsed} 
        onCollapse={setCollapsed}
        theme="dark"
      >
        <div className="logo">
          <h2 style={{ 
            color: 'white', 
            textAlign: 'center', 
            margin: '16px 0',
            fontSize: collapsed ? '16px' : '18px'
          }}>
            {collapsed ? 'AI' : '🤖 AI客服'}
          </h2>
        </div>
        <Menu
          theme="dark"
          selectedKeys={[getSelectedKey()]}
          mode="inline"
          onClick={handleMenuClick}
        >
          {menuItems.map(item => (
            <Menu.Item key={item.key} icon={item.icon}>
              {item.label}
            </Menu.Item>
          ))}
        </Menu>
      </Sider>
      
      <Layout>
        <Header style={{ 
          background: '#fff', 
          padding: '0 24px',
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
        }}>
          <div style={{ 
            display: 'flex', 
            justifyContent: 'space-between', 
            alignItems: 'center',
            height: '100%'
          }}>
            <h1 style={{ margin: 0, color: '#1890ff' }}>
              AI 智能客服系统
            </h1>
            <div style={{ color: '#666' }}>
              多 Key 轮询 | 实时监控 | 智能对话
            </div>
          </div>
        </Header>
        
        <Content style={{ margin: 0 }}>
          <ErrorBoundary>
            <Routes>
              <Route path="/" element={<ChatPage />} />
              <Route path="/monitor" element={<MonitorPage />} />
              <Route path="/test" element={<TestPage />} />
              <Route path="/config" element={<ConfigPage />} />
            </Routes>
          </ErrorBoundary>
        </Content>
      </Layout>
    </Layout>
  );
};

function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

export default App;