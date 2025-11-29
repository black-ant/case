package com.example.dl4j.persistence;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 模型保存与加载示例
 * 
 * 本示例演示：
 * 1. 训练一个简单模型
 * 2. 保存模型到磁盘
 * 3. 从磁盘加载模型
 * 4. 验证加载的模型
 * 
 * @author Deeplearning4j Demo
 */
public class ModelSaveLoadDemo {

    public static void main(String[] args) {
        System.out.println("========== 模型保存与加载示例 ==========\n");

        try {
            // 1. 创建并训练模型
            System.out.println("步骤 1: 创建并训练模型");
            MultiLayerNetwork originalModel = createAndTrainModel();
            System.out.println("模型训练完成\n");

            // 2. 测试原始模型
            INDArray testInput = Nd4j.create(new double[][]{{0.5, 0.5}});
            INDArray originalOutput = originalModel.output(testInput);
            System.out.println("原始模型预测输出: " + originalOutput);
            System.out.println();

            // 3. 保存模型
            System.out.println("步骤 2: 保存模型");
            saveModel(originalModel);

            // 4. 加载模型
            System.out.println("\n步骤 3: 加载模型");
            MultiLayerNetwork loadedModel = loadModel();

            // 5. 验证加载的模型
            System.out.println("\n步骤 4: 验证加载的模型");
            verifyModel(loadedModel, testInput, originalOutput);

            // 6. 高级保存选项演示
            System.out.println("\n步骤 5: 高级保存选项");
            demonstrateAdvancedSaving(originalModel);

        } catch (IOException e) {
            System.err.println("文件操作错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建并训练一个简单的模型
     */
    private static MultiLayerNetwork createAndTrainModel() {
        // 创建简单的异或问题数据
        INDArray input = Nd4j.create(new double[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        });

        INDArray labels = Nd4j.create(new double[][]{
            {1, 0},
            {0, 1},
            {0, 1},
            {1, 0}
        });

        DataSet trainingData = new DataSet(input, labels);

        // 构建网络
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(12345)
            .weightInit(WeightInit.XAVIER)
            .updater(new Adam(0.1))
            .list()
            .layer(new DenseLayer.Builder()
                .nIn(2)
                .nOut(4)
                .activation(Activation.RELU)
                .build())
            .layer(new OutputLayer.Builder()
                .nIn(4)
                .nOut(2)
                .activation(Activation.SOFTMAX)
                .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .build())
            .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        // 训练模型
        System.out.println("训练模型...");
        for (int i = 0; i < 1000; i++) {
            model.fit(trainingData);
            if ((i + 1) % 200 == 0) {
                System.out.printf("  Iteration %d - Loss: %.6f%n", i + 1, model.score());
            }
        }

        return model;
    }

    /**
     * 保存模型到磁盘
     */
    private static void saveModel(MultiLayerNetwork model) throws IOException {
        // 方式1: 保存完整模型（包含优化器状态）
        File completeModel = new File("model-complete.zip");
        ModelSerializer.writeModel(model, completeModel, true);
        System.out.println("✓ 保存完整模型: " + completeModel.getAbsolutePath());
        System.out.println("  文件大小: " + completeModel.length() + " 字节");

        // 方式2: 保存模型（不包含优化器状态）
        File modelOnly = new File("model-only.zip");
        ModelSerializer.writeModel(model, modelOnly, false);
        System.out.println("\n✓ 保存模型（无优化器）: " + modelOnly.getAbsolutePath());
        System.out.println("  文件大小: " + modelOnly.length() + " 字节");

        // 方式3: 仅保存配置
        String configJson = model.getLayerWiseConfigurations().toJson();
        Files.write(Paths.get("model-config.json"), configJson.getBytes());
        System.out.println("\n✓ 保存模型配置: model-config.json");

        // 方式4: 仅保存参数
        INDArray params = model.params();
        Nd4j.saveBinary(params, new File("model-params.bin"));
        System.out.println("✓ 保存模型参数: model-params.bin");
    }

    /**
     * 从磁盘加载模型
     */
    private static MultiLayerNetwork loadModel() throws IOException {
        File modelFile = new File("model-complete.zip");

        if (!modelFile.exists()) {
            throw new IOException("模型文件不存在: " + modelFile.getAbsolutePath());
        }

        System.out.println("从文件加载模型: " + modelFile.getAbsolutePath());
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFile);

        System.out.println("✓ 模型加载成功");
        System.out.println("  层数: " + model.getnLayers());
        System.out.println("  参数总数: " + model.numParams());

        return model;
    }

    /**
     * 验证加载的模型
     */
    private static void verifyModel(MultiLayerNetwork loadedModel, INDArray testInput, INDArray originalOutput) {
        // 使用相同的输入进行预测
        INDArray loadedOutput = loadedModel.output(testInput);

        System.out.println("原始模型输出: " + originalOutput);
        System.out.println("加载模型输出: " + loadedOutput);

        // 计算输出差异
        INDArray diff = originalOutput.sub(loadedOutput);
        double maxDiff = diff.abs().maxNumber().doubleValue();

        System.out.println("\n最大输出差异: " + maxDiff);

        if (maxDiff < 1e-6) {
            System.out.println("✓ 验证成功！模型输出完全一致");
        } else if (maxDiff < 1e-3) {
            System.out.println("⚠ 验证通过，但存在小的数值差异（可接受）");
        } else {
            System.out.println("✗ 验证失败！模型输出存在显著差异");
        }
    }

    /**
     * 演示高级保存选项
     */
    private static void demonstrateAdvancedSaving(MultiLayerNetwork model) throws IOException {
        System.out.println("=== 高级保存选项 ===\n");

        // 1. 检查点保存（带版本号）
        System.out.println("1. 检查点保存");
        for (int checkpoint = 1; checkpoint <= 3; checkpoint++) {
            String filename = String.format("model-checkpoint-%03d.zip", checkpoint);
            ModelSerializer.writeModel(model, new File(filename), true);
            System.out.println("  ✓ 保存检查点: " + filename);
        }

        // 2. 最佳模型保存
        System.out.println("\n2. 最佳模型保存");
        double currentAccuracy = 0.95;  // 假设的准确率
        File bestModelFile = new File("model-best.zip");

        // 通常在验证集上评估后决定是否保存
        if (!bestModelFile.exists() || shouldSaveNewBest(currentAccuracy)) {
            ModelSerializer.writeModel(model, bestModelFile, true);
            System.out.println("  ✓ 保存最佳模型 (准确率: " + currentAccuracy + ")");

            // 同时保存元数据
            String metadata = String.format(
                "{\"accuracy\": %.4f, \"timestamp\": \"%s\", \"epochs\": %d}",
                currentAccuracy,
                java.time.LocalDateTime.now(),
                1000
            );
            Files.write(Paths.get("model-best.meta.json"), metadata.getBytes());
            System.out.println("  ✓ 保存模型元数据");
        }

        // 3. 跨版本兼容性
        System.out.println("\n3. 确保跨版本兼容性");
        System.out.println("  - 保存完整配置（JSON）");
        System.out.println("  - 记录 DL4J 版本信息");
        System.out.println("  - 保存数据预处理参数");

        System.out.println("\n所有文件已保存到当前目录");
    }

    /**
     * 判断是否应该保存新的最佳模型
     */
    private static boolean shouldSaveNewBest(double currentAccuracy) {
        // 简化版本：实际应用中应该读取之前的最佳准确率进行比较
        return true;
    }
}
