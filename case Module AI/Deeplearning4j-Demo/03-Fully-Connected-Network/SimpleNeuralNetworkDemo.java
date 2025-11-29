package com.example.dl4j.network;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * 简单神经网络示例 - 解决 XOR 问题
 * 
 * XOR（异或）问题是一个经典的非线性分类问题：
 * - 输入：2个二进制数
 * - 输出：当且仅当两个输入不同时为 1
 * 
 * 真值表：
 * 0 XOR 0 = 0
 * 0 XOR 1 = 1
 * 1 XOR 0 = 1
 * 1 XOR 1 = 0
 * 
 * 这个问题无法用单层感知机解决，需要至少一个隐藏层。
 * 
 * @author Deeplearning4j Demo
 */
public class SimpleNeuralNetworkDemo {

    public static void main(String[] args) {
        System.out.println("========== 简单神经网络 - XOR 问题 ==========\n");

        // 1. 准备训练数据
        DataSet trainingData = createXORDataSet();
        System.out.println("训练数据：");
        System.out.println("输入（特征）：\n" + trainingData.getFeatures());
        System.out.println("\n输出（标签）：\n" + trainingData.getLabels());
        System.out.println();

        // 2. 构建神经网络
        MultiLayerNetwork model = buildNeuralNetwork();
        System.out.println("网络架构：");
        System.out.println(model.summary());
        System.out.println();

        // 3. 训练模型
        System.out.println("开始训练...");
        trainModel(model, trainingData);
        System.out.println();

        // 4. 测试模型
        testModel(model, trainingData);

        // 5. 可视化决策边界（输出分析）
        analyzeDecisionBoundary(model);
    }

    /**
     * 创建 XOR 训练数据集
     */
    private static DataSet createXORDataSet() {
        // 输入：4个样本，每个2个特征
        INDArray input = Nd4j.create(new double[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        });

        // 输出：4个样本，2个类别（one-hot 编码）
        // [1, 0] 表示类别 0（XOR 结果为 0）
        // [0, 1] 表示类别 1（XOR 结果为 1）
        INDArray output = Nd4j.create(new double[][]{
            {1, 0},  // 0 XOR 0 = 0
            {0, 1},  // 0 XOR 1 = 1
            {0, 1},  // 1 XOR 0 = 1
            {1, 0}   // 1 XOR 1 = 0
        });

        return new DataSet(input, output);
    }

    /**
     * 构建神经网络
     * 
     * 网络结构：
     * - 输入层：2 个神经元（2个特征）
     * - 隐藏层：4 个神经元，ReLU 激活
     * - 输出层：2 个神经元（2个类别），Softmax 激活
     */
    private static MultiLayerNetwork buildNeuralNetwork() {
        int seed = 12345;
        int numInputs = 2;
        int numHidden = 4;
        int numOutputs = 2;
        double learningRate = 0.1;

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(seed)
            .weightInit(WeightInit.XAVIER)
            .updater(new Adam(learningRate))
            .list()
            // 隐藏层
            .layer(0, new DenseLayer.Builder()
                .nIn(numInputs)
                .nOut(numHidden)
                .activation(Activation.RELU)
                .build())
            // 输出层
            .layer(1, new OutputLayer.Builder()
                .nIn(numHidden)
                .nOut(numOutputs)
                .activation(Activation.SOFTMAX)
                .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .build())
            .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        // 设置监听器，每100次迭代输出一次损失
        model.setListeners(new ScoreIterationListener(100));

        return model;
    }

    /**
     * 训练模型
     */
    private static void trainModel(MultiLayerNetwork model, DataSet trainingData) {
        int numEpochs = 1000;

        for (int epoch = 0; epoch < numEpochs; epoch++) {
            model.fit(trainingData);

            // 每100个epoch输出一次训练状态
            if ((epoch + 1) % 100 == 0) {
                double score = model.score();
                System.out.printf("Epoch %d/%d - Loss: %.6f%n", epoch + 1, numEpochs, score);
            }
        }

        System.out.println("训练完成！");
    }

    /**
     * 测试模型
     */
    private static void testModel(MultiLayerNetwork model, DataSet testData) {
        System.out.println("测试模型：");
        System.out.println("-".repeat(50));

        INDArray input = testData.getFeatures();
        INDArray labels = testData.getLabels();

        // 进行预测
        INDArray predictions = model.output(input);

        System.out.println("输入 -> 期望输出 -> 预测输出 -> 预测类别");
        for (int i = 0; i < input.rows(); i++) {
            INDArray inputRow = input.getRow(i);
            INDArray labelRow = labels.getRow(i);
            INDArray predRow = predictions.getRow(i);

            // 获取预测的类别（概率最大的）
            int predictedClass = Nd4j.argMax(predRow, 1).getInt(0);
            int actualClass = Nd4j.argMax(labelRow, 1).getInt(0);

            System.out.printf("%s -> %d -> [%.4f, %.4f] -> %d %s%n",
                inputRow,
                actualClass,
                predRow.getDouble(0),
                predRow.getDouble(1),
                predictedClass,
                (predictedClass == actualClass) ? "✓" : "✗"
            );
        }

        // 使用 Evaluation 类计算指标
        Evaluation eval = new Evaluation(2);
        eval.eval(labels, predictions);
        System.out.println("\n评估结果：");
        System.out.println(eval.stats());
    }

    /**
     * 分析决策边界
     */
    private static void analyzeDecisionBoundary(MultiLayerNetwork model) {
        System.out.println("决策边界分析：");
        System.out.println("-".repeat(50));

        // 在 [0, 1] x [0, 1] 区域内采样
        int resolution = 11;  // 每个维度采样11个点
        System.out.println("网格采样预测（数值越接近1表示预测为类别1）：\n");

        for (int i = resolution - 1; i >= 0; i--) {
            double y = i / (resolution - 1.0);
            System.out.printf("y=%.1f: ", y);

            for (int j = 0; j < resolution; j++) {
                double x = j / (resolution - 1.0);

                INDArray input = Nd4j.create(new double[][]{{x, y}});
                INDArray output = model.output(input);

                // 获取类别1的概率
                double prob = output.getDouble(0, 1);

                // 可视化：用字符表示概率
                char symbol;
                if (prob < 0.2) symbol = ' ';
                else if (prob < 0.4) symbol = '.';
                else if (prob < 0.6) symbol = 'o';
                else if (prob < 0.8) symbol = 'O';
                else symbol = '@';

                System.out.print(symbol + " ");
            }
            System.out.println();
        }

        System.out.println("\n图例：' '=类别0, '.'到'@'=逐渐接近类别1");
    }
}
