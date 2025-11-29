# Deeplearning4j 快速开始指南

## 🚀 项目概览

本项目包含 9 个完整的知识点模块，每个模块都有详细文档和可运行的 Java 示例代码。

## 📋 前置要求

- **Java**: JDK 8 或更高版本
- **Maven**: 3.6+ 
- **IDE**: IntelliJ IDEA 或 Eclipse（推荐）
- **内存**: 建议至少 4GB RAM
- **（可选）CUDA**: 10.0+ 用于 GPU 加速

## ⚙️ 环境配置

### 1. 验证 Java 安装

```bash
java -version
```

应该看到类似输出：
```
java version "1.8.0_xxx" 或更高
```

### 2. 验证 Maven 安装

```bash
mvn -version
```

### 3. 构建项目

```bash
cd Deeplearning4j-Demo
mvn clean install
```

## 📚 学习路径

### 🔰 初学者路径（推荐顺序）

#### 第1步：NDArray 基础
```bash
cd 01-NDArray-Basics
mvn exec:java -Dexec.mainClass="com.example.dl4j.ndarray.NDArrayBasicsDemo"
mvn exec:java -Dexec.mainClass="com.example.dl4j.ndarray.NDArrayMathDemo"
```

**学习要点**：
- 掌握 NDArray 创建和操作
- 理解形状和维度
- 熟悉数学运算

#### 第2步：数据预处理
```bash
cd ../02-Data-Preprocessing
mvn exec:java -Dexec.mainClass="com.example.dl4j.preprocessing.DataSetBasicsDemo"
mvn exec:java -Dexec.mainClass="com.example.dl4j.preprocessing.DataNormalizationDemo"
```

**学习要点**：
- DataSet 的创建和使用
- 数据归一化技术
- 数据划分策略

#### 第3步：全连接神经网络
```bash
cd ../03-Fully-Connected-Network
mvn exec:java -Dexec.mainClass="com.example.dl4j.network.SimpleNeuralNetworkDemo"
```

**学习要点**：
- 构建第一个神经网络
- 理解训练流程
- 解决 XOR 问题

#### 第4步：模型保存与加载
```bash
cd ../07-Model-Persistence
mvn exec:java -Dexec.mainClass="com.example.dl4j.persistence.ModelSaveLoadDemo"
```

**学习要点**：
- 模型序列化
- 检查点保存
- 模型部署准备

### 🎯 进阶路径

#### CNN - 图像处理
```bash
cd ../04-Convolutional-Neural-Network
mvn exec:java -Dexec.mainClass="com.example.dl4j.cnn.LeNetDemo"
```

#### RNN/LSTM - 序列数据
```bash
cd ../05-Recurrent-Neural-Network
mvn exec:java -Dexec.mainClass="com.example.dl4j.rnn.LSTMTimeSeriesDemo"
```

#### 超参数优化
```bash
cd ../09-Hyperparameter-Optimization
mvn exec:java -Dexec.mainClass="com.example.dl4j.hyperparameter.SimpleHyperparameterSearchDemo"
```

## 🛠️ IDE 配置

### IntelliJ IDEA

1. **导入项目**
   - File → Open → 选择 `pom.xml`
   - 等待 Maven 依赖下载完成

2. **运行示例**
   - 打开任意 `.java` 文件
   - 右键 → Run 'ClassName.main()'

3. **增加内存**（如果需要）
   - Run → Edit Configurations
   - VM options: `-Xmx4g -Xms1g`

### Eclipse

1. **导入项目**
   - File → Import → Maven → Existing Maven Projects
   - 选择项目目录

2. **运行示例**
   - 右键 Java 文件 → Run As → Java Application

## 📝 项目结构

```
Deeplearning4j-Demo/
├── pom.xml                              # Maven 配置
├── README.md                            # 项目主文档
├── QUICKSTART.md                        # 本文件
├── 01-NDArray-Basics/                   # NDArray 基础
│   ├── README.md
│   ├── NDArrayBasicsDemo.java
│   └── NDArrayMathDemo.java
├── 02-Data-Preprocessing/               # 数据预处理
│   ├── README.md
│   ├── DataSetBasicsDemo.java
│   └── DataNormalizationDemo.java
├── 03-Fully-Connected-Network/          # 全连接网络
│   ├── README.md
│   └── SimpleNeuralNetworkDemo.java
├── 04-Convolutional-Neural-Network/     # CNN
│   ├── README.md
│   └── LeNetDemo.java
├── 05-Recurrent-Neural-Network/         # RNN/LSTM
│   ├── README.md
│   └── LSTMTimeSeriesDemo.java
├── 06-Model-Training-Evaluation/        # 训练与评估
│   └── README.md
├── 07-Model-Persistence/                # 模型保存
│   ├── README.md
│   └── ModelSaveLoadDemo.java
├── 08-Transfer-Learning/                # 迁移学习
│   └── README.md
└── 09-Hyperparameter-Optimization/      # 超参数优化
    ├── README.md
    └── SimpleHyperparameterSearchDemo.java
```

## 🎓 学习建议

### 每个模块的学习方式

1. **阅读 README.md**
   - 理解核心概念
   - 查看代码示例
   - 注意最佳实践

2. **运行示例代码**
   - 观察输出结果
   - 理解每一步的作用
   - 尝试修改参数

3. **动手实践**
   - 修改代码参数
   - 尝试不同配置
   - 解决练习问题

4. **记录学习笔记**
   - 记录关键概念
   - 总结常见问题
   - 记录实验结果

## 💡 常见问题排查

### 问题1：Maven 依赖下载缓慢

**解决方案**：使用国内镜像
```xml
<!-- 在 settings.xml 中添加阿里云镜像 -->
<mirror>
  <id>aliyun</id>
  <mirrorOf>central</mirrorOf>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

### 问题2：内存不足错误

**解决方案**：增加 JVM 堆内存
```bash
export MAVEN_OPTS="-Xmx8g -Xms2g"
# Windows PowerShell:
$env:MAVEN_OPTS="-Xmx8g -Xms2g"
```

### 问题3：找不到类或方法

**解决方案**：
1. 确保 Maven 依赖已下载：`mvn dependency:resolve`
2. 清理并重新构建：`mvn clean install`
3. 刷新 IDE 项目

### 问题4：训练速度慢

**解决方案**：
1. 减少训练数据量（测试时）
2. 使用 GPU 加速（修改 pom.xml 中的 nd4j 后端）
3. 减少网络层数或神经元数量

## 🔧 高级配置

### 启用 GPU 支持

修改 `pom.xml`：
```xml
<!-- 注释掉 CPU 后端 -->
<!--
<dependency>
    <groupId>org.nd4j</groupId>
    <artifactId>nd4j-native-platform</artifactId>
    <version>${nd4j.version}</version>
</dependency>
-->

<!-- 添加 GPU 后端 -->
<dependency>
    <groupId>org.nd4j</groupId>
    <artifactId>nd4j-cuda-11.0-platform</artifactId>
    <version>${nd4j.version}</version>
</dependency>
```

### 启用训练可视化

在代码中添加：
```java
UIServer uiServer = UIServer.getInstance();
StatsStorage statsStorage = new InMemoryStatsStorage();
model.setListeners(new StatsListener(statsStorage));
uiServer.attach(statsStorage);
```

访问：`http://localhost:9000`

## 📖 进一步学习资源

### 官方资源
- [DL4J 官方网站](https://deeplearning4j.konduit.ai/)
- [API 文档](https://deeplearning4j.konduit.ai/api/latest/)
- [示例代码库](https://github.com/eclipse/deeplearning4j-examples)

### 社区资源
- [GitHub Issues](https://github.com/eclipse/deeplearning4j/issues)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/deeplearning4j)
- [Gitter Chat](https://gitter.im/deeplearning4j/deeplearning4j)

### 推荐书籍
- 《Deep Learning》- Ian Goodfellow
- 《Hands-On Machine Learning》- Aurélien Géron
- 《Neural Networks and Deep Learning》- Michael Nielsen

## 🤝 贡献指南

发现问题或有改进建议？欢迎：
1. 提交 Issue
2. 创建 Pull Request
3. 分享学习心得

## 📄 许可证

MIT License

---

**开始你的深度学习之旅！** 🚀

如有问题，请查看各模块的 README.md 或提交 Issue。
