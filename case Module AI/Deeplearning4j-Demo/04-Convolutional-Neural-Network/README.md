# 卷积神经网络（CNN）

## 概述

卷积神经网络（Convolutional Neural Network, CNN）是专门用于处理具有网格结构数据（如图像）的深度学习模型。CNN 通过卷积层、池化层等特殊结构，能够自动学习图像的层次化特征。

## 核心组件

### 1. 卷积层（Convolution Layer）

卷积层通过卷积核（filter）在输入上滑动，提取局部特征。

```java
.layer(new ConvolutionLayer.Builder(5, 5)  // 5x5 卷积核
    .nIn(1)              // 输入通道数
    .nOut(20)            // 输出特征图数量（卷积核数量）
    .stride(1, 1)        // 步长
    .activation(Activation.RELU)
    .build())
```

**关键参数：**
- **kernel size**: 卷积核大小，常用 3x3, 5x5
- **stride**: 步长，通常为 1
- **padding**: 填充方式（SAME/VALID）
- **filters**: 卷积核数量

### 2. 池化层（Pooling Layer）

池化层降低特征图的空间维度，减少参数量和计算量。

```java
.layer(new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
    .kernelSize(2, 2)    // 池化窗口大小
    .stride(2, 2)        // 步长
    .build())
```

**池化类型：**
- **Max Pooling**: 取窗口内最大值（最常用）
- **Average Pooling**: 取窗口内平均值
- **Global Pooling**: 对整个特征图池化

### 3. 经典 CNN 架构

#### LeNet-5（1998）
最早的 CNN 之一，用于手写数字识别：
```
输入(28x28) -> Conv(5x5,6) -> Pool(2x2) -> Conv(5x5,16) -> Pool(2x2) -> FC(120) -> FC(84) -> 输出(10)
```

#### AlexNet（2012）
ImageNet 突破性架构：
- 使用 ReLU 激活函数
- Dropout 正则化
- 数据增强

#### VGG（2014）
使用小卷积核（3x3）的深层网络：
- 简单而深（16-19层）
- 统一使用 3x3 卷积核

#### ResNet（2015）
引入残差连接，训练超深网络：
- 跳跃连接解决梯度消失
- 152层甚至更深

### 4. 数据格式

CNN 输入数据格式：`[batch, channels, height, width]`

```java
// 灰度图像：1个通道
INDArray grayImage = Nd4j.create(batchSize, 1, 28, 28);

// 彩色图像：3个通道（RGB）
INDArray rgbImage = Nd4j.create(batchSize, 3, 224, 224);
```

### 5. 批归一化（Batch Normalization）

加速训练并提高稳定性：

```java
.layer(new ConvolutionLayer.Builder(3, 3)
    .nIn(64)
    .nOut(64)
    .activation(Activation.RELU)
    .build())
.layer(new BatchNormalization())
```

### 6. 典型 CNN 结构

```java
MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
    .seed(12345)
    .updater(new Adam(0.001))
    .list()
    // 第一个卷积块
    .layer(new ConvolutionLayer.Builder(5, 5)
        .nIn(1)
        .nOut(32)
        .stride(1, 1)
        .activation(Activation.RELU)
        .build())
    .layer(new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
        .kernelSize(2, 2)
        .stride(2, 2)
        .build())
    
    // 第二个卷积块
    .layer(new ConvolutionLayer.Builder(3, 3)
        .nIn(32)
        .nOut(64)
        .stride(1, 1)
        .activation(Activation.RELU)
        .build())
    .layer(new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
        .kernelSize(2, 2)
        .stride(2, 2)
        .build())
    
    // 全连接层
    .layer(new DenseLayer.Builder()
        .nOut(128)
        .activation(Activation.RELU)
        .dropOut(0.5)
        .build())
    
    // 输出层
    .layer(new OutputLayer.Builder()
        .nOut(10)
        .activation(Activation.SOFTMAX)
        .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
        .build())
    
    .setInputType(InputType.convolutionalFlat(28, 28, 1))
    .build();
```

## CNN 设计原则

### 1. 特征图数量
- 随着网络深度增加，特征图数量通常递增
- 常见模式：32 -> 64 -> 128 -> 256

### 2. 感受野
- 使用多个小卷积核（3x3）比大卷积核更好
- 可以堆叠多个卷积层增加感受野

### 3. 下采样策略
- 使用池化层或 stride > 1 的卷积降维
- 每次下采样后，特征图数量通常翻倍

### 4. 全局池化
- 在全连接层前使用全局池化
- 减少参数，防止过拟合

## 示例代码说明

### LeNetDemo.java
经典 LeNet-5 架构实现，MNIST 数据集训练。

### CNNImageClassificationDemo.java
完整的图像分类流程，包括数据加载、训练、评估。

### TransferLearningCNNDemo.java
使用预训练模型进行迁移学习。

## 最佳实践

1. **数据预处理**
   - 归一化像素值到 [0, 1] 或 [-1, 1]
   - 数据增强：翻转、旋转、裁剪

2. **网络设计**
   - 从简单模型开始
   - 使用 3x3 卷积核
   - 使用批归一化加速训练

3. **训练技巧**
   - 使用 Adam 优化器
   - 学习率衰减
   - 使用 Dropout 防止过拟合

4. **调试**
   - 可视化特征图
   - 监控梯度
   - 使用预训练权重

## 常见问题

**Q: 如何选择卷积核大小？**
A: 通常使用 3x3，少数情况用 5x5 或 7x7（第一层）。

**Q: 池化层必需吗？**
A: 不是必需的，可以用 stride=2 的卷积替代。

**Q: 为什么 CNN 比全连接网络好？**
A: 
- 参数共享：卷积核在整个图像上共享
- 局部连接：利用图像的空间结构
- 平移不变性：对位置变化鲁棒

**Q: 如何处理不同大小的输入图像？**
A: 
- Resize 到固定大小
- 使用全局池化
- 使用 Spatial Pyramid Pooling

## 下一步

学习完 CNN 后，继续学习：
- [循环神经网络](../05-Recurrent-Neural-Network/) - 处理序列数据
- [迁移学习](../08-Transfer-Learning/) - 使用预训练模型
- [模型训练与评估](../06-Model-Training-Evaluation/) - 优化训练过程
