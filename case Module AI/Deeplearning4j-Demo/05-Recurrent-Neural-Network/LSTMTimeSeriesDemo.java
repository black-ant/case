package com.example.dl4j.rnn;

import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * LSTM 时间序列预测示例
 * 
 * 本示例演示如何使用 LSTM 网络预测正弦波序列。
 * 
 * 任务：给定过去的 N 个时间步，预测下一个时间步的值。
 * 
 * 网络结构：
 * 输入(seqLength, 1) -> LSTM(128) -> LSTM(64) -> 输出(1)
 * 
 * @author Deeplearning4j Demo
 */
public class LSTMTimeSeriesDemo {

    // 网络参数
    private static final int INPUT_SIZE = 1;        // 输入特征维度
    private static final int HIDDEN_SIZE_1 = 128;   // 第一层 LSTM 隐藏单元数
    private static final int HIDDEN_SIZE_2 = 64;    // 第二层 LSTM 隐藏单元数
    private static final int OUTPUT_SIZE = 1;       // 输出维度
    private static final int SEQ_LENGTH = 50;       // 序列长度（时间步）
    
    // 训练参数
    private static final int EPOCHS = 50;
    private static final double LEARNING_RATE = 0.001;
    private static final int SEED = 12345;

    public static void main(String[] args) {
        System.out.println("========== LSTM 时间序列预测示例 ==========\n");

        // 1. 生成训练数据（正弦波）
        System.out.println("生成时间序列数据...");
        DataSet trainData = generateSineWaveData(1000, SEQ_LENGTH);
        DataSet testData = generateSineWaveData(200, SEQ_LENGTH);

        System.out.println("训练集: " + trainData.numExamples() + " 个序列");
        System.out.println("测试集: " + testData.numExamples() + " 个序列");
        System.out.println();

        // 2. 构建 LSTM 模型
        MultiLayerNetwork model = buildLSTMModel();
        System.out.println("LSTM 网络架构：");
        System.out.println(model.summary());
        System.out.println();

        // 3. 训练模型
        System.out.println("开始训练...");
        trainModel(model, trainData);

        // 4. 评估和预测
        System.out.println("\n评估模型...");
        evaluateModel(model, testData);

        // 5. 单步预测演示
        demonstratePrediction(model);
    }

    /**
     * 构建 LSTM 模型
     */
    private static MultiLayerNetwork buildLSTMModel() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(SEED)
            .weightInit(WeightInit.XAVIER)
            .updater(new Adam(LEARNING_RATE))
            .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
            .gradientNormalizationThreshold(1.0)
            .list()
            
            // 第一层 LSTM
            .layer(0, new LSTM.Builder()
                .nIn(INPUT_SIZE)
                .nOut(HIDDEN_SIZE_1)
                .activation(Activation.TANH)
                .build())
            
            // 第二层 LSTM
            .layer(1, new LSTM.Builder()
                .nIn(HIDDEN_SIZE_1)
                .nOut(HIDDEN_SIZE_2)
                .activation(Activation.TANH)
                .build())
            
            // 输出层
            .layer(2, new RnnOutputLayer.Builder()
                .nIn(HIDDEN_SIZE_2)
                .nOut(OUTPUT_SIZE)
                .activation(Activation.IDENTITY)
                .lossFunction(LossFunctions.LossFunction.MSE)
                .build())
            
            .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));

        return model;
    }

    /**
     * 生成正弦波时间序列数据
     */
    private static DataSet generateSineWaveData(int numSamples, int seqLength) {
        // 输入形状: [batch, features, timeSteps]
        INDArray input = Nd4j.create(numSamples, INPUT_SIZE, seqLength);
        // 标签形状: [batch, features, timeSteps]
        INDArray labels = Nd4j.create(numSamples, OUTPUT_SIZE, seqLength);

        for (int i = 0; i < numSamples; i++) {
            // 随机起始相位
            double phase = Math.random() * 2 * Math.PI;
            // 随机频率
            double frequency = 0.05 + Math.random() * 0.05;

            for (int t = 0; t < seqLength; t++) {
                // 生成正弦波值
                double value = Math.sin(phase + frequency * t);
                input.putScalar(new int[]{i, 0, t}, value);

                // 标签是下一个时间步的值（这里简化为当前步，实际应用中可能需要预测未来）
                labels.putScalar(new int[]{i, 0, t}, value);
            }
        }

        return new DataSet(input, labels);
    }

    /**
     * 训练模型
     */
    private static void trainModel(MultiLayerNetwork model, DataSet trainData) {
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            model.fit(trainData);

            if ((epoch + 1) % 5 == 0) {
                double score = model.score();
                System.out.printf("Epoch %d/%d - Loss: %.6f%n", epoch + 1, EPOCHS, score);
            }
        }

        System.out.println("训练完成！");
    }

    /**
     * 评估模型
     */
    private static void evaluateModel(MultiLayerNetwork model, DataSet testData) {
        // 预测
        INDArray predictions = model.output(testData.getFeatures());
        INDArray labels = testData.getLabels();

        // 计算 MSE
        INDArray diff = predictions.sub(labels);
        INDArray squared = diff.mul(diff);
        double mse = squared.meanNumber().doubleValue();

        // 计算 MAE
        double mae = diff.abs().meanNumber().doubleValue();

        // 计算 RMSE
        double rmse = Math.sqrt(mse);

        System.out.println("测试集评估结果：");
        System.out.println("-".repeat(50));
        System.out.printf("MSE (均方误差): %.6f%n", mse);
        System.out.printf("MAE (平均绝对误差): %.6f%n", mae);
        System.out.printf("RMSE (均方根误差): %.6f%n", rmse);
    }

    /**
     * 单步预测演示
     */
    private static void demonstratePrediction(MultiLayerNetwork model) {
        System.out.println("\n单步预测演示：");
        System.out.println("-".repeat(50));

        // 生成一个测试序列
        double phase = 0.0;
        double frequency = 0.05;

        INDArray input = Nd4j.create(1, INPUT_SIZE, SEQ_LENGTH);
        System.out.println("输入序列（正弦波）：");

        for (int t = 0; t < SEQ_LENGTH; t++) {
            double value = Math.sin(phase + frequency * t);
            input.putScalar(new int[]{0, 0, t}, value);

            if (t < 10 || t >= SEQ_LENGTH - 5) {
                System.out.printf("t=%d: %.4f%n", t, value);
            } else if (t == 10) {
                System.out.println("...");
            }
        }

        // 预测
        INDArray prediction = model.output(input);

        System.out.println("\n预测结果（部分）：");
        for (int t = 0; t < Math.min(10, SEQ_LENGTH); t++) {
            double pred = prediction.getDouble(0, 0, t);
            double actual = Math.sin(phase + frequency * t);
            double error = Math.abs(pred - actual);

            System.out.printf("t=%d: 预测=%.4f, 实际=%.4f, 误差=%.4f%n", t, pred, actual, error);
        }

        // 预测未来值（多步预测）
        System.out.println("\n多步预测（递归）：");
        INDArray currentSeq = input.dup();

        for (int step = 0; step < 10; step++) {
            // 预测
            INDArray nextPred = model.output(currentSeq);

            // 获取最后一个时间步的预测值
            double predValue = nextPred.getDouble(0, 0, SEQ_LENGTH - 1);

            // 真实的下一个值
            double actualValue = Math.sin(phase + frequency * (SEQ_LENGTH + step));

            System.out.printf("未来步 %d: 预测=%.4f, 实际=%.4f%n", step + 1, predValue, actualValue);

            // 更新序列（将预测值作为新输入）
            // 移除第一个时间步，添加预测值到末尾
            INDArray shifted = Nd4j.create(1, INPUT_SIZE, SEQ_LENGTH);
            for (int t = 0; t < SEQ_LENGTH - 1; t++) {
                shifted.putScalar(new int[]{0, 0, t}, currentSeq.getDouble(0, 0, t + 1));
            }
            shifted.putScalar(new int[]{0, 0, SEQ_LENGTH - 1}, predValue);
            currentSeq = shifted;
        }
    }
}
