package com.example.dl4j.hyperparameter;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterov;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的超参数搜索示例
 * 
 * 本示例演示手动进行超参数搜索的基本方法：
 * 1. 定义参数网格
 * 2. 遍历所有组合
 * 3. 训练和评估模型
 * 4. 选择最佳配置
 * 
 * @author Deeplearning4j Demo
 */
public class SimpleHyperparameterSearchDemo {

    public static void main(String[] args) {
        System.out.println("========== 简单超参数搜索示例 ==========\n");

        // 1. 准备数据
        DataSet trainData = createTrainingData();
        DataSet testData = createTestData();

        // 2. 定义超参数搜索空间
        double[] learningRates = {0.001, 0.01, 0.1};
        int[] hiddenLayerSizes = {4, 8, 16};
        Activation[] activations = {Activation.RELU, Activation.TANH};
        String[] optimizers = {"Adam", "SGD", "Nesterov"};

        // 3. 执行网格搜索
        List<SearchResult> results = gridSearch(
            trainData, testData,
            learningRates, hiddenLayerSizes, activations, optimizers
        );

        // 4. 显示结果
        displayResults(results);

        // 5. 使用最佳配置训练最终模型
        trainFinalModel(results.get(0), trainData, testData);
    }

    /**
     * 网格搜索
     */
    private static List<SearchResult> gridSearch(
            DataSet trainData, DataSet testData,
            double[] learningRates, int[] layerSizes,
            Activation[] activations, String[] optimizers) {

        List<SearchResult> results = new ArrayList<>();
        int totalCombinations = learningRates.length * layerSizes.length * 
                                activations.length * optimizers.length;
        int current = 0;

        System.out.println("开始网格搜索...");
        System.out.println("总共 " + totalCombinations + " 种组合\n");

        // 遍历所有超参数组合
        for (double lr : learningRates) {
            for (int size : layerSizes) {
                for (Activation activation : activations) {
                    for (String optimizer : optimizers) {
                        current++;
                        System.out.printf("进度: %d/%d - 测试配置: lr=%.4f, size=%d, act=%s, opt=%s%n",
                            current, totalCombinations, lr, size, activation, optimizer);

                        // 构建模型
                        MultiLayerNetwork model = buildModel(lr, size, activation, optimizer);

                        // 训练模型
                        long startTime = System.currentTimeMillis();
                        trainModel(model, trainData);
                        long trainTime = System.currentTimeMillis() - startTime;

                        // 评估模型
                        double accuracy = evaluateModel(model, testData);

                        // 记录结果
                        SearchResult result = new SearchResult(
                            lr, size, activation, optimizer, accuracy, trainTime
                        );
                        results.add(result);

                        System.out.printf("  准确率: %.4f, 训练时间: %d ms%n%n", accuracy, trainTime);
                    }
                }
            }
        }

        // 按准确率排序
        results.sort((a, b) -> Double.compare(b.accuracy, a.accuracy));

        return results;
    }

    /**
     * 构建模型
     */
    private static MultiLayerNetwork buildModel(
            double learningRate, int hiddenSize,
            Activation activation, String optimizer) {

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(12345)
            .weightInit(WeightInit.XAVIER)
            .updater(getUpdater(optimizer, learningRate))
            .list()
            .layer(new DenseLayer.Builder()
                .nIn(2)
                .nOut(hiddenSize)
                .activation(activation)
                .build())
            .layer(new OutputLayer.Builder()
                .nIn(hiddenSize)
                .nOut(2)
                .activation(Activation.SOFTMAX)
                .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .build())
            .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        return model;
    }

    /**
     * 获取优化器
     */
    private static org.nd4j.linalg.learning.config.IUpdater getUpdater(String name, double lr) {
        switch (name) {
            case "Adam":
                return new Adam(lr);
            case "SGD":
                return new Sgd(lr);
            case "Nesterov":
                return new Nesterov(lr, 0.9);
            default:
                return new Adam(lr);
        }
    }

    /**
     * 训练模型
     */
    private static void trainModel(MultiLayerNetwork model, DataSet trainData) {
        int epochs = 500;
        for (int i = 0; i < epochs; i++) {
            model.fit(trainData);
        }
    }

    /**
     * 评估模型
     */
    private static double evaluateModel(MultiLayerNetwork model, DataSet testData) {
        INDArray predictions = model.output(testData.getFeatures());
        Evaluation eval = new Evaluation(2);
        eval.eval(testData.getLabels(), predictions);
        return eval.accuracy();
    }

    /**
     * 显示搜索结果
     */
    private static void displayResults(List<SearchResult> results) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("搜索结果（按准确率排序）");
        System.out.println("=".repeat(70));

        System.out.println(String.format("%-4s | %-8s | %-6s | %-10s | %-10s | %-8s | %-10s",
            "排名", "学习率", "层大小", "激活函数", "优化器", "准确率", "训练时间"));
        System.out.println("-".repeat(70));

        int rank = 1;
        for (SearchResult result : results.subList(0, Math.min(10, results.size()))) {
            System.out.println(String.format("%-4d | %.6f | %-6d | %-10s | %-10s | %.4f | %d ms",
                rank++,
                result.learningRate,
                result.hiddenSize,
                result.activation,
                result.optimizer,
                result.accuracy,
                result.trainTime
            ));
        }

        System.out.println("\n最佳配置:");
        SearchResult best = results.get(0);
        System.out.println("  学习率: " + best.learningRate);
        System.out.println("  隐藏层大小: " + best.hiddenSize);
        System.out.println("  激活函数: " + best.activation);
        System.out.println("  优化器: " + best.optimizer);
        System.out.println("  准确率: " + String.format("%.4f", best.accuracy));
    }

    /**
     * 使用最佳配置训练最终模型
     */
    private static void trainFinalModel(SearchResult bestConfig, DataSet trainData, DataSet testData) {
        System.out.println("\n使用最佳配置训练最终模型...");

        MultiLayerNetwork finalModel = buildModel(
            bestConfig.learningRate,
            bestConfig.hiddenSize,
            bestConfig.activation,
            bestConfig.optimizer
        );

        // 训练更多轮次
        for (int i = 0; i < 1000; i++) {
            finalModel.fit(trainData);
        }

        double finalAccuracy = evaluateModel(finalModel, testData);
        System.out.println("最终模型准确率: " + String.format("%.4f", finalAccuracy));
    }

    /**
     * 创建训练数据（XOR 问题）
     */
    private static DataSet createTrainingData() {
        INDArray input = Nd4j.create(new double[][]{
            {0, 0}, {0, 1}, {1, 0}, {1, 1}
        });
        INDArray labels = Nd4j.create(new double[][]{
            {1, 0}, {0, 1}, {0, 1}, {1, 0}
        });
        return new DataSet(input, labels);
    }

    /**
     * 创建测试数据
     */
    private static DataSet createTestData() {
        return createTrainingData();
    }

    /**
     * 搜索结果类
     */
    static class SearchResult {
        double learningRate;
        int hiddenSize;
        Activation activation;
        String optimizer;
        double accuracy;
        long trainTime;

        SearchResult(double learningRate, int hiddenSize, Activation activation,
                     String optimizer, double accuracy, long trainTime) {
            this.learningRate = learningRate;
            this.hiddenSize = hiddenSize;
            this.activation = activation;
            this.optimizer = optimizer;
            this.accuracy = accuracy;
            this.trainTime = trainTime;
        }
    }
}
