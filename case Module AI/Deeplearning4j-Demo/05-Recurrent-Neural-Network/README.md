# 循环神经网络（RNN/LSTM）

## 概述

循环神经网络（Recurrent Neural Network, RNN）是专门处理序列数据的神经网络架构。通过循环连接，RNN 能够保持"记忆"，适用于时间序列、文本、语音等序列数据。

## 核心概念

### 1. 基本 RNN

RNN 通过隐藏状态在时间步之间传递信息：

```java
.layer(new RnnOutputLayer.Builder()
    .nIn(inputSize)
    .nOut(outputSize)
    .activation(Activation.SOFTMAX)
    .lossFunction(LossFunctions.LossFunction.MCXENT)
    .build())
```

**问题**：
- 梯度消失/爆炸
- 难以学习长期依赖

### 2. LSTM（长短期记忆网络）

LSTM 通过门控机制解决长期依赖问题：

```java
.layer(new LSTM.Builder()
    .nIn(inputSize)
    .nOut(hiddenSize)
    .activation(Activation.TANH)
    .build())
```

**核心组件**：
- **遗忘门**：决定丢弃哪些信息
- **输入门**：决定更新哪些信息
- **输出门**：决定输出什么信息

### 3. GRU（门控循环单元）

GRU 是 LSTM 的简化版本，参数更少：

```java
.layer(new GRU.Builder()
    .nIn(inputSize)
    .nOut(hiddenSize)
    .activation(Activation.TANH)
    .build())
```

**对比**：
- GRU：更简单，训练更快
- LSTM：更灵活，表达能力更强

### 4. 序列数据格式

RNN 输入格式：`[batch, features, timeSteps]`

```java
// 时间序列数据：批次大小32，5个特征，100个时间步
INDArray input = Nd4j.create(32, 5, 100);
```

### 5. 常见应用场景

#### 多对一（Many-to-One）
情感分类、序列分类：
```
输入序列 -> RNN -> 单个输出
```

#### 一对多（One-to-Many）
图像描述生成：
```
单个输入 -> RNN -> 输出序列
```

#### 多对多（Many-to-Many）
机器翻译、序列标注：
```
输入序列 -> RNN -> 输出序列
```

### 6. 典型 RNN 架构

```java
MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
    .seed(12345)
    .updater(new Adam(0.001))
    .list()
    // LSTM 层1
    .layer(new LSTM.Builder()
        .nIn(inputSize)
        .nOut(256)
        .activation(Activation.TANH)
        .build())
    // Dropout
    .layer(new DropoutLayer(0.2))
    // LSTM 层2
    .layer(new LSTM.Builder()
        .nIn(256)
        .nOut(128)
        .activation(Activation.TANH)
        .build())
    // 输出层
    .layer(new RnnOutputLayer.Builder()
        .nIn(128)
        .nOut(numClasses)
        .activation(Activation.SOFTMAX)
        .lossFunction(LossFunctions.LossFunction.MCXENT)
        .build())
    .build();
```

## 时间序列预测

### 单步预测
预测下一个时间点的值：
```java
// 输入：过去N个时间步
// 输出：下一个时间步
```

### 多步预测
预测未来多个时间点：
```java
// 方法1：递归预测（自回归）
// 方法2：直接多输出
```

### 序列到序列
输入和输出都是序列：
```java
// Encoder-Decoder 架构
```

## 文本处理

### 1. 字符级 RNN
```java
// 输入：字符序列
// 输出：下一个字符
// 应用：文本生成
```

### 2. 词级 RNN
```java
// 使用词嵌入（Word Embedding）
.layer(new EmbeddingLayer.Builder()
    .nIn(vocabSize)
    .nOut(embeddingSize)
    .build())
```

### 3. 序列分类
```java
// 情感分析、垃圾邮件检测
// 使用 last time step 或 attention
```

## 示例代码说明

### LSTMTimeSeriesDemo.java
使用 LSTM 进行时间序列预测。

### TextGenerationDemo.java
字符级 RNN 文本生成示例。

### SentimentAnalysisDemo.java
使用 LSTM 进行情感分类。

## 训练技巧

### 1. 处理变长序列
```java
// 使用 masking 处理变长输入
// 填充（padding）+ mask array
```

### 2. 梯度裁剪
```java
.updater(new Adam(0.001))
.gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
.gradientNormalizationThreshold(1.0)
```

### 3. 截断反向传播
```java
// Truncated BPTT
.backpropType(BackpropType.TruncatedBPTT)
.tBPTTForwardLength(50)
.tBPTTBackwardLength(50)
```

## 最佳实践

1. **数据预处理**
   - 归一化时间序列数据
   - 使用滑动窗口创建序列
   - 处理缺失值

2. **网络设计**
   - 首选 LSTM/GRU 而非基本 RNN
   - 使用双向 RNN 捕获双向上下文
   - 多层堆叠提升表达能力

3. **训练策略**
   - 使用梯度裁剪防止梯度爆炸
   - 适当的序列长度（避免过长）
   - 使用 teacher forcing（序列生成）

4. **调试**
   - 监控梯度范数
   - 可视化隐藏状态
   - 从简单任务开始

## 常见问题

**Q: LSTM vs GRU 如何选择？**
A: 数据充足用 LSTM，资源有限或快速原型用 GRU。

**Q: 如何处理长序列？**
A: 
- 使用截断 BPTT
- 增加隐藏层大小
- 使用 attention 机制

**Q: RNN 训练不收敛？**
A:
- 检查梯度（可能爆炸或消失）
- 降低学习率
- 使用梯度裁剪
- 尝试不同的初始化

**Q: 如何提升性能？**
A:
- 增加层数和隐藏单元
- 使用双向 RNN
- 添加 attention 机制
- 尝试 Transformer（更现代）

## 下一步

学习完 RNN/LSTM 后，继续学习：
- [模型训练与评估](../06-Model-Training-Evaluation/) - 优化训练
- [模型保存与加载](../07-Model-Persistence/) - 模型部署
- [超参数优化](../09-Hyperparameter-Optimization/) - 自动调参
