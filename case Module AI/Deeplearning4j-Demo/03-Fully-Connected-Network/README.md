# 全连接神经网络（Fully Connected Network）

## 概述

全连接神经网络（也称为前馈神经网络或多层感知机）是最基础的深度学习架构。每一层的所有神经元都与下一层的所有神经元相连。

## 核心概念

### 1. 网络架构

一个典型的全连接网络包含：
- **输入层**：接收原始特征
- **隐藏层**：一个或多个全连接层
- **输出层**：产生最终预测

### 2. MultiLayerConfiguration

DL4J 使用构建器模式配置网络：

```java
MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
    .seed(12345)
    .updater(new Adam(0.001))
    .list()
    .layer(new DenseLayer.Builder()
        .nIn(784)
        .nOut(128)
        .activation(Activation.RELU)
        .build())
    .layer(new OutputLayer.Builder()
        .nIn(128)
        .nOut(10)
        .activation(Activation.SOFTMAX)
        .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
        .build())
    .build();
```

### 3. 层类型

#### DenseLayer（全连接层）
```java
new DenseLayer.Builder()
    .nIn(inputSize)      // 输入特征数
    .nOut(outputSize)    // 输出神经元数
    .activation(Activation.RELU)
    .build()
```

#### OutputLayer（输出层）
```java
new OutputLayer.Builder()
    .nIn(hiddenSize)
    .nOut(numClasses)
    .activation(Activation.SOFTMAX)
    .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
    .build()
```

### 4. 激活函数

常用激活函数及其应用场景：

| 激活函数 | 特点 | 适用场景 |
|---------|------|---------|
| **ReLU** | 计算简单，缓解梯度消失 | 隐藏层（默认首选） |
| **Sigmoid** | 输出 [0, 1] | 二分类输出层 |
| **Softmax** | 输出概率分布 | 多分类输出层 |
| **Tanh** | 输出 [-1, 1] | 隐藏层（数据中心化） |
| **LeakyReLU** | 解决 ReLU 死神经元 | 深层网络隐藏层 |

### 5. 损失函数

根据任务选择损失函数：

- **分类任务**
  - 二分类：`XENT`（交叉熵）
  - 多分类：`NEGATIVELOGLIKELIHOOD`（负对数似然）
  
- **回归任务**
  - `MSE`（均方误差）
  - `MAE`（平均绝对误差）

### 6. 优化器

优化算法选择：

```java
// SGD（随机梯度下降）
.updater(new Sgd(0.01))

// Adam（自适应学习率，推荐）
.updater(new Adam(0.001))

// RMSProp
.updater(new RMSProp(0.001))

// Nesterov Momentum
.updater(new Nesterov(0.01, 0.9))
```

**推荐**：
- 初学者/快速原型：Adam (lr=0.001)
- 需要精细调优：SGD with Momentum
- RNN/LSTM：RMSProp 或 Adam

### 7. 正则化技术

防止过拟合的方法：

#### Dropout
```java
.layer(new DenseLayer.Builder()
    .nIn(512)
    .nOut(256)
    .activation(Activation.RELU)
    .dropOut(0.5)  // 50% dropout
    .build())
```

#### L2 正则化
```java
.l2(0.0005)  // 权重衰减
```

#### Early Stopping
```java
EarlyStoppingConfiguration esConf = new EarlyStoppingConfiguration.Builder()
    .epochTerminationConditions(new MaxEpochsTerminationCondition(100))
    .scoreCalculator(new DataSetLossCalculator(testData, true))
    .evaluateEveryNEpochs(1)
    .modelSaver(new LocalFileModelSaver(directory))
    .build();
```

## 网络设计指南

### 隐藏层数量
- **1 层**：简单的线性可分问题
- **2-3 层**：大多数问题的良好起点
- **4+ 层**：复杂问题，但需注意过拟合

### 神经元数量
- 通常从输入层逐渐减少到输出层
- 常见模式：`[输入] -> [512] -> [256] -> [128] -> [输出]`
- 太少：欠拟合
- 太多：过拟合 + 计算昂贵

### 学习率选择
- 太大：训练不稳定，损失震荡
- 太小：收敛太慢
- 推荐起点：0.001（Adam），0.01（SGD）
- 使用学习率调度动态调整

## 示例代码说明

### SimpleNeuralNetworkDemo.java
构建一个简单的两层神经网络，解决 XOR 问题。

### IrisClassificationDemo.java
使用经典的 Iris 数据集进行多分类任务。

### MNISTDemo.java
手写数字识别（MNIST 数据集）完整示例。

### RegressionDemo.java
使用神经网络进行回归预测。

## 训练流程

```java
// 1. 配置网络
MultiLayerConfiguration conf = ...

// 2. 初始化模型
MultiLayerNetwork model = new MultiLayerNetwork(conf);
model.init();

// 3. 设置监听器
model.setListeners(new ScoreIterationListener(100));

// 4. 训练模型
for (int epoch = 0; epoch < numEpochs; epoch++) {
    model.fit(trainData);
}

// 5. 评估模型
Evaluation eval = model.evaluate(testData);
System.out.println(eval.stats());
```

## 最佳实践

1. **数据预处理**
   - 始终归一化输入数据
   - 使用合适的 batch size（通常 32-256）

2. **网络初始化**
   - 使用 Xavier 或 He 初始化
   - 设置随机种子以便复现

3. **训练技巧**
   - 从简单模型开始，逐步增加复杂度
   - 监控训练和验证损失
   - 使用 early stopping 防止过拟合

4. **调试**
   - 先在小数据集上验证
   - 检查梯度是否正常
   - 可视化训练过程

## 常见问题

**Q: 网络不收敛怎么办？**
A: 
1. 降低学习率
2. 检查数据归一化
3. 尝试不同的激活函数
4. 增加训练数据

**Q: 过拟合如何解决？**
A:
1. 增加训练数据
2. 使用 Dropout
3. 添加 L2 正则化
4. 减少网络复杂度
5. 使用 Early Stopping

**Q: 训练太慢？**
A:
1. 使用 GPU
2. 增加 batch size
3. 减少网络规模
4. 使用更快的优化器（Adam）

## 下一步

掌握全连接网络后，可以学习：
- [卷积神经网络](../04-Convolutional-Neural-Network/) - 处理图像数据
- [循环神经网络](../05-Recurrent-Neural-Network/) - 处理序列数据
- [模型训练与评估](../06-Model-Training-Evaluation/) - 深入训练技巧
