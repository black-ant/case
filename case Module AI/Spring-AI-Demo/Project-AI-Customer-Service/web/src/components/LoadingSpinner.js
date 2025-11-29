import React from 'react';
import { Spin } from 'antd';

const LoadingSpinner = ({ 
  size = 'default', 
  tip = '加载中...', 
  style = {},
  spinning = true 
}) => {
  return (
    <div 
      style={{ 
        display: 'flex', 
        justifyContent: 'center', 
        alignItems: 'center', 
        minHeight: '200px',
        ...style 
      }}
    >
      <Spin size={size} tip={tip} spinning={spinning} />
    </div>
  );
};

export default LoadingSpinner;