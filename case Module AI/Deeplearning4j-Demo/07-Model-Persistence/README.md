# 模型保存与加载

## 概述

训练好的模型需要保存到磁盘，以便在生产环境中使用或继续训练。Deeplearning4j 提供了 `ModelSerializer` 工具类来实现模型的序列化和反序列化。

## 核心API

### ModelSerializer

```java
import org.deeplearning4j.util.ModelSerializer;
```

## 模型保存

### 1. 保存完整模型

保存模型架构、权重和配置：

```java
MultiLayerNetwork model = ...;  // 训练好的模型

// 保存模型
File modelFile = new File("model.zip");
ModelSerializer.writeModel(model, modelFile, true);
```

**参数说明**：
- `model`: 要保存的模型
- `file`: 保存路径
- `saveUpdater`: 是否保存优化器状态（继续训练需要）

### 2. 仅保存模型配置

```java
// 保存配置（JSON 格式）
MultiLayerConfiguration conf = model.getLayerWiseConfigurations();
String json = conf.toJson();
Files.write(Paths.get("model-config.json"), json.getBytes());
```

### 3. 仅保存模型参数

```java
// 保存权重参数
INDArray params = model.params();
Nd4j.saveBinary(params, new File("model-params.bin"));
```

## 模型加载

### 1. 加载完整模型

```java
// 加载模型
File modelFile = new File("model.zip");
MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFile);

// 直接使用
INDArray input = ...;
INDArray output = model.output(input);
```

### 2. 加载计算图模型

对于 ComputationGraph 类型的模型：

```java
ComputationGraph model = ModelSerializer.restoreComputationGraph(modelFile);
```

### 3. 从配置文件恢复

```java
// 加载配置
String json = new String(Files.readAllBytes(Paths.get("model-config.json")));
MultiLayerConfiguration conf = MultiLayerConfiguration.fromJson(json);

// 创建新模型
MultiLayerNetwork newModel = new MultiLayerNetwork(conf);
newModel.init();

// 加载参数
INDArray params = Nd4j.readBinary(new File("model-params.bin"));
newModel.setParams(params);
```

## 检查点（Checkpointing）

在训练过程中定期保存模型：

```java
int epoch = 0;
int saveFrequency = 5;  // 每5个epoch保存一次

for (epoch = 0; epoch < numEpochs; epoch++) {
    model.fit(trainData);
    
    // 定期保存检查点
    if ((epoch + 1) % saveFrequency == 0) {
        File checkpoint = new File("model-checkpoint-epoch-" + (epoch + 1) + ".zip");
        ModelSerializer.writeModel(model, checkpoint, true);
        System.out.println("Checkpoint saved: " + checkpoint.getName());
    }
}

// 保存最终模型
ModelSerializer.writeModel(model, new File("model-final.zip"), true);
```

## 归一化器保存

如果使用了数据归一化，需要同时保存归一化器：

```java
// 训练时
NormalizerStandardize normalizer = new NormalizerStandardize();
normalizer.fit(trainData);
trainData.setPreProcessor(normalizer);

// 保存归一化器
NormalizerSerializer.getDefault().write(normalizer, "normalizer.bin");

// 加载时
NormalizerStandardize loadedNormalizer = 
    NormalizerSerializer.getDefault().restore("normalizer.bin");

// 应用到新数据
testData.setPreProcessor(loadedNormalizer);
```

## 模型版本管理

### 最佳实践

```java
// 使用时间戳和版本号
String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
String modelName = String.format("model-v%d-%s.zip", version, timestamp);

// 保存模型
ModelSerializer.writeModel(model, new File(modelName), true);

// 保存元数据
Map<String, String> metadata = new HashMap<>();
metadata.put("version", String.valueOf(version));
metadata.put("accuracy", String.valueOf(accuracy));
metadata.put("timestamp", timestamp);
metadata.put("dataset", "MNIST");

// 保存到 JSON
String metadataJson = new Gson().toJson(metadata);
Files.write(Paths.get(modelName + ".meta.json"), metadataJson.getBytes());
```

## 示例代码说明

### ModelSaveLoadDemo.java
演示模型的基本保存和加载操作。

### CheckpointingDemo.java
展示训练过程中的检查点保存。

### NormalizerPersistenceDemo.java
演示归一化器的保存和加载。

## 生产部署

### 1. 模型加载优化

```java
// 使用流式加载（大模型）
InputStream is = new FileInputStream("large-model.zip");
MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(is, true);
```

### 2. 模型预热

```java
// 首次推理时预热模型
INDArray dummyInput = Nd4j.zeros(1, inputSize);
model.output(dummyInput);  // 预热

// 后续推理会更快
INDArray realInput = ...;
INDArray prediction = model.output(realInput);
```

### 3. 批量推理

```java
// 批量处理提高吞吐量
int batchSize = 32;
List<INDArray> inputs = ...;

for (int i = 0; i < inputs.size(); i += batchSize) {
    int end = Math.min(i + batchSize, inputs.size());
    INDArray batch = Nd4j.vstack(inputs.subList(i, end));
    INDArray predictions = model.output(batch);
    // 处理预测结果
}
```

## 模型压缩

### 权重量化

```java
// 将 float32 权重转换为 float16（减少模型大小）
INDArray params = model.params();
INDArray quantized = params.castTo(DataType.HALF);
```

### 模型剪枝

```java
// 移除小权重（稀疏化）
double threshold = 0.01;
INDArray params = model.params();
INDArray mask = params.abs().gt(threshold);
params.muli(mask);
```

## 最佳实践

1. **保存策略**
   - 训练时保存多个检查点
   - 保存最佳模型（基于验证集）
   - 保存最终模型

2. **文件命名**
   - 使用有意义的名称
   - 包含版本号和时间戳
   - 记录元数据

3. **测试加载**
   - 保存后立即测试加载
   - 验证模型输出一致性
   - 确保跨平台兼容性

4. **版本控制**
   - 使用 Git LFS 存储大模型
   - 维护模型变更日志
   - 记录训练超参数

## 常见问题

**Q: 模型文件太大怎么办？**
A: 
- 使用模型压缩技术
- 仅保存必要的参数
- 考虑量化和剪枝

**Q: 如何确保模型兼容性？**
A: 
- 记录 DL4J 版本
- 保存完整配置
- 进行兼容性测试

**Q: 加载模型后预测结果不一致？**
A:
- 检查归一化器是否正确加载
- 验证输入格式
- 确保模型模式（train/test）正确

## 下一步

学习完模型持久化后，继续学习：
- [迁移学习](../08-Transfer-Learning/) - 使用预训练模型
- [模型训练与评估](../06-Model-Training-Evaluation/) - 优化训练流程
