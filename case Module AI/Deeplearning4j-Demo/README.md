# Deeplearning4j 学习指南

欢迎来到 Deeplearning4j 学习项目！本项目系统地梳理了 Deeplearning4j 框架的核心知识点，每个知识点都包含详细的文档说明和可运行的 Java 示例代码。

## 关于 Deeplearning4j

Deeplearning4j（DL4J）是一个为 Java 和 JVM 设计的开源深度学习库。它支持各种神经网络架构，并与 Hadoop 和 Spark 集成，适合企业级的分布式深度学习应用。

### 主要特点
- ✅ 纯 Java 实现，与 JVM 生态系统无缝集成
- ✅ 支持 CPU 和 GPU 加速（CUDA）
- ✅ 分布式训练支持（Apache Spark）
- ✅ 丰富的神经网络层和架构
- ✅ 与 Keras 模型导入兼容

## 项目结构

本项目按知识点分类，每个文件夹包含：
- 📄 `README.md` - 知识点详细说明
- ☕ `*.java` - 可运行的示例代码

### 知识点目录

#### 1️⃣ [NDArray 基础操作](./01-NDArray-Basics/)
学习 ND4J（N-Dimensional Arrays for Java）的基础操作，这是 Deeplearning4j 的数值计算基础。

**核心内容：**
- NDArray 的创建与初始化
- 基本数学运算（加减乘除）
- 矩阵操作（转置、点乘）
- 索引与切片
- 广播机制

#### 2️⃣ [数据预处理](./02-Data-Preprocessing/)
数据预处理是机器学习的关键步骤，学习如何准备和处理训练数据。

**核心内容：**
- DataSet 和 DataSetIterator
- 数据归一化与标准化
- 数据增强
- CSV/图像数据加载
- 训练集/测试集划分

#### 3️⃣ [全连接神经网络](./03-Fully-Connected-Network/)
构建最基础的前馈神经网络，理解深度学习的核心概念。

**核心内容：**
- MultiLayerConfiguration 配置
- DenseLayer（全连接层）
- 激活函数（ReLU、Sigmoid、Tanh）
- 损失函数
- 优化器（SGD、Adam）

#### 4️⃣ [卷积神经网络 CNN](./04-Convolutional-Neural-Network/)
学习图像识别和计算机视觉任务的核心网络架构。

**核心内容：**
- ConvolutionLayer（卷积层）
- SubsamplingLayer（池化层）
- 经典 CNN 架构（LeNet、VGG、ResNet）
- 图像分类实战
- 特征可视化

#### 5️⃣ [循环神经网络 RNN/LSTM](./05-Recurrent-Neural-Network/)
处理序列数据和时间序列问题的专用网络架构。

**核心内容：**
- RNN 基础概念
- LSTM 和 GRU
- 时间序列预测
- 文本生成
- 序列分类

#### 6️⃣ [模型训练与评估](./06-Model-Training-Evaluation/)
掌握模型训练的最佳实践和性能评估方法。

**核心内容：**
- 训练监听器（ScoreIterationListener）
- 早停策略（Early Stopping）
- 学习率调度
- 评估指标（Accuracy、Precision、Recall、F1）
- 混淆矩阵

#### 7️⃣ [模型保存与加载](./07-Model-Persistence/)
学习如何持久化训练好的模型并在生产环境中使用。

**核心内容：**
- 模型保存（ModelSerializer）
- 模型加载与推理
- 检查点（Checkpoint）
- 模型导出格式

#### 8️⃣ [迁移学习](./08-Transfer-Learning/)
利用预训练模型加速训练和提升性能。

**核心内容：**
- 预训练模型导入
- 冻结层（Frozen Layers）
- 微调（Fine-tuning）
- Keras 模型导入
- VGG、ResNet 等预训练模型使用

#### 9️⃣ [超参数优化](./09-Hyperparameter-Optimization/)
自动化搜索最优模型参数。

**核心内容：**
- 网格搜索（Grid Search）
- 随机搜索（Random Search）
- Arbiter 库使用
- 参数空间定义
- 最佳模型选择

## 快速开始

### 环境要求
- Java 8 或更高版本
- Maven 3.6+
- （可选）CUDA 10.0+ 用于 GPU 加速

### 安装步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd Deeplearning4j-Demo
```

2. **构建项目**
```bash
mvn clean install
```

3. **运行示例**
```bash
# 运行某个具体示例
mvn exec:java -Dexec.mainClass="com.example.NDArrayBasicsDemo"
```

### Maven 依赖

本项目的核心依赖包括：

```xml
<dependencies>
    <!-- DL4J 核心库 -->
    <dependency>
        <groupId>org.deeplearning4j</groupId>
        <artifactId>deeplearning4j-core</artifactId>
        <version>1.0.0-M2.1</version>
    </dependency>
    
    <!-- ND4J 后端（CPU） -->
    <dependency>
        <groupId>org.nd4j</groupId>
        <artifactId>nd4j-native-platform</artifactId>
        <version>1.0.0-M2.1</version>
    </dependency>
</dependencies>
```

## 学习路径建议

### 初学者路径
1. 从 **NDArray 基础操作** 开始，熟悉数值计算
2. 学习 **数据预处理**，理解数据准备流程
3. 实践 **全连接神经网络**，构建第一个模型
4. 掌握 **模型训练与评估**，学会调试模型

### 进阶路径
1. 深入 **CNN**，处理图像任务
2. 学习 **RNN/LSTM**，处理序列数据
3. 应用 **迁移学习**，提升模型效果
4. 使用 **超参数优化**，自动化调参

### 实战路径
1. 选择一个实际问题（图像分类、时间序列预测等）
2. 完成完整的数据准备流程
3. 实验多种网络架构
4. 调优并部署最佳模型

## 常见问题

### Q: 如何选择 CPU 还是 GPU 后端？
A: 修改 `pom.xml` 中的依赖：
- CPU: `nd4j-native-platform`
- GPU: `nd4j-cuda-11.0-platform`（根据你的 CUDA 版本选择）

### Q: 内存不足怎么办？
A: 增加 JVM 堆内存：
```bash
export MAVEN_OPTS="-Xmx8g -Xms2g"
```

### Q: 训练速度慢怎么优化？
A: 
- 使用 GPU 加速
- 增加 batch size
- 使用数据预加载
- 启用 CuDNN（GPU）

## 资源链接

- 🌐 [Deeplearning4j 官网](https://deeplearning4j.konduit.ai/)
- 📚 [官方文档](https://deeplearning4j.konduit.ai/deeplearning4j/how-to-guides)
- 💬 [社区论坛](https://community.konduit.ai/)
- 📖 [API 文档](https://deeplearning4j.konduit.ai/api/latest/)
- 🐙 [GitHub 仓库](https://github.com/eclipse/deeplearning4j)

## 贡献

欢迎提交 Issue 和 Pull Request！

## 许可证

MIT License

---

**开始学习之旅吧！** 🚀
