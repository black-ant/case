# 数据预处理

## 概述

数据预处理是深度学习工作流中最重要的步骤之一。Deeplearning4j 提供了 DataVec 库来处理各种数据格式和预处理任务。

## 核心组件

### 1. DataSet 和 DataSetIterator

**DataSet** 是 DL4J 中表示训练数据的基本单位：
- 包含特征（features）和标签（labels）
- 支持批处理（mini-batch）

**DataSetIterator** 用于遍历数据：
- 自动批处理
- 支持数据打乱（shuffle）
- 内存高效的数据加载

### 2. 数据归一化

归一化将数据缩放到特定范围，常见方法：

#### Min-Max 归一化
将数据缩放到 [0, 1] 或 [-1, 1] 范围：
```java
NormalizerMinMaxScaler scaler = new NormalizerMinMaxScaler(0, 1);
scaler.fit(dataSet);
scaler.transform(dataSet);
```

#### 标准化（Z-score）
将数据转换为均值为 0、标准差为 1：
```java
NormalizerStandardize normalizer = new NormalizerStandardize();
normalizer.fit(dataSet);
normalizer.transform(dataSet);
```

### 3. 数据加载

#### CSV 数据加载
```java
RecordReader recordReader = new CSVRecordReader(0, ',');
recordReader.initialize(new FileSplit(new File("data.csv")));
DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize);
```

#### 图像数据加载
```java
ImageRecordReader recordReader = new ImageRecordReader(height, width, channels);
recordReader.initialize(new FileSplit(imageDir));
DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize);
```

### 4. 数据增强

数据增强通过变换增加训练数据的多样性：

```java
ImageTransform transform = new MultiImageTransform(
    new FlipImageTransform(),
    new WarpImageTransform(10),
    new RotateImageTransform(15)
);
```

### 5. 数据划分

将数据划分为训练集和测试集：

```java
SplitTestAndTrain split = allData.splitTestAndTrain(0.8);
DataSet trainData = split.getTrain();
DataSet testData = split.getTest();
```

## 示例代码说明

### DataSetBasicsDemo.java
演示 DataSet 的创建和基本操作。

### DataNormalizationDemo.java
展示各种数据归一化技术。

### CSVDataLoadingDemo.java
从 CSV 文件加载数据的完整示例。

### ImageDataLoadingDemo.java
处理图像数据的示例。

## 最佳实践

1. **始终归一化数据**
   - 加速训练收敛
   - 提高模型稳定性
   - 避免梯度爆炸/消失

2. **使用 Iterator 而非一次性加载**
   - 节省内存
   - 支持大规模数据集
   - 更好的灵活性

3. **数据增强策略**
   - 仅在训练时应用
   - 避免过度增强导致失真
   - 根据任务选择合适的增强方法

4. **保存归一化参数**
   - 训练后保存 normalizer
   - 推理时使用相同的归一化
   - 确保一致性

## 常见问题

**Q: 应该在划分数据前还是后归一化？**
A: 先划分，然后在训练集上 fit normalizer，再分别 transform 训练集和测试集。

**Q: 什么时候使用 Min-Max vs 标准化？**
A: Min-Max 适合有界数据，标准化适合正态分布数据。实际中可以都尝试。

**Q: 如何处理不平衡数据？**
A: 使用重采样、类权重调整、或合成新样本（SMOTE）。

## 下一步

学习完数据预处理后，继续学习：
- [全连接神经网络](../03-Fully-Connected-Network/) - 开始构建模型
- [模型训练与评估](../06-Model-Training-Evaluation/) - 训练和评估模型
