package com.example.dl4j.cnn;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
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
 * LeNet-5 卷积神经网络示例
 * 
 * LeNet-5 是 Yann LeCun 在 1998 年提出的经典 CNN 架构，
 * 最初用于手写数字识别（MNIST）。
 * 
 * 网络结构：
 * 输入(28x28x1) 
 * -> Conv1(5x5, 6 filters) -> ReLU -> MaxPool(2x2)
 * -> Conv2(5x5, 16 filters) -> ReLU -> MaxPool(2x2)
 * -> Flatten
 * -> FC1(120) -> ReLU
 * -> FC2(84) -> ReLU
 * -> 输出(10) -> Softmax
 * 
 * @author Deeplearning4j Demo
 */
public class LeNetDemo {

    // 图像参数
    private static final int HEIGHT = 28;
    private static final int WIDTH = 28;
    private static final int CHANNELS = 1;  // 灰度图像
    private static final int NUM_CLASSES = 10;

    // 训练参数
    private static final int BATCH_SIZE = 64;
    private static final int EPOCHS = 10;
    private static final double LEARNING_RATE = 0.001;
    private static final int SEED = 12345;

    public static void main(String[] args) {
        System.out.println("========== LeNet-5 卷积神经网络示例 ==========\n");

        // 1. 构建 LeNet-5 模型
        MultiLayerNetwork model = buildLeNet();
        System.out.println("LeNet-5 网络架构：");
        System.out.println(model.summary());
        System.out.println();

        // 2. 创建模拟数据（实际应用中应使用真实的 MNIST 数据）
        System.out.println("生成模拟训练数据...");
        DataSet trainData = createMockData(1000);
        DataSet testData = createMockData(200);

        // 3. 训练模型
        System.out.println("\n开始训练...");
        trainModel(model, trainData);

        // 4. 评估模型
        System.out.println("\n评估模型...");
        evaluateModel(model, testData);

        // 5. 分析网络参数
        analyzeNetwork(model);
    }

    /**
     * 构建 LeNet-5 网络
     */
    private static MultiLayerNetwork buildLeNet() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(SEED)
            .weightInit(WeightInit.XAVIER)
            .updater(new Adam(LEARNING_RATE))
            .list()
            
            // 第一个卷积块：Conv -> Pool
            .layer(0, new ConvolutionLayer.Builder(5, 5)
                .nIn(CHANNELS)
                .nOut(6)
                .stride(1, 1)
                .activation(Activation.RELU)
                .build())
            .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(2, 2)
                .build())
            
            // 第二个卷积块：Conv -> Pool
            .layer(2, new ConvolutionLayer.Builder(5, 5)
                .nIn(6)
                .nOut(16)
                .stride(1, 1)
                .activation(Activation.RELU)
                .build())
            .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(2, 2)
                .build())
            
            // 全连接层
            .layer(4, new DenseLayer.Builder()
                .nOut(120)
                .activation(Activation.RELU)
                .build())
            .layer(5, new DenseLayer.Builder()
                .nOut(84)
                .activation(Activation.RELU)
                .build())
            
            // 输出层
            .layer(6, new OutputLayer.Builder()
                .nOut(NUM_CLASSES)
                .activation(Activation.SOFTMAX)
                .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .build())
            
            // 设置输入类型
            .setInputType(InputType.convolutionalFlat(HEIGHT, WIDTH, CHANNELS))
            .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));

        return model;
    }

    /**
     * 创建模拟数据（实际应用中使用真实数据集）
     */
    private static DataSet createMockData(int numSamples) {
        // 创建随机图像数据：[samples, channels, height, width]
        INDArray features = Nd4j.rand(numSamples, CHANNELS, HEIGHT, WIDTH);

        // 创建随机标签（one-hot 编码）
        INDArray labels = Nd4j.zeros(numSamples, NUM_CLASSES);
        for (int i = 0; i < numSamples; i++) {
            int label = (int) (Math.random() * NUM_CLASSES);
            labels.putScalar(new int[]{i, label}, 1.0);
        }

        return new DataSet(features, labels);
    }

    /**
     * 训练模型
     */
    private static void trainModel(MultiLayerNetwork model, DataSet trainData) {
        // 分批训练
        int numBatches = trainData.numExamples() / BATCH_SIZE;

        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            // 打乱数据
            trainData.shuffle();

            // 按批次训练
            for (int batch = 0; batch < numBatches; batch++) {
                int start = batch * BATCH_SIZE;
                int end = Math.min(start + BATCH_SIZE, trainData.numExamples());

                DataSet batchData = new DataSet(
                    trainData.getFeatures().get(
                        org.nd4j.linalg.indexing.NDArrayIndex.interval(start, end),
                        org.nd4j.linalg.indexing.NDArrayIndex.all(),
                        org.nd4j.linalg.indexing.NDArrayIndex.all(),
                        org.nd4j.linalg.indexing.NDArrayIndex.all()
                    ),
                    trainData.getLabels().get(
                        org.nd4j.linalg.indexing.NDArrayIndex.interval(start, end),
                        org.nd4j.linalg.indexing.NDArrayIndex.all()
                    )
                );

                model.fit(batchData);
            }

            // 输出每个 epoch 的损失
            double score = model.score();
            System.out.printf("Epoch %d/%d - Loss: %.6f%n", epoch + 1, EPOCHS, score);
        }

        System.out.println("训练完成！");
    }

    /**
     * 评估模型
     */
    private static void evaluateModel(MultiLayerNetwork model, DataSet testData) {
        // 预测
        INDArray predictions = model.output(testData.getFeatures());

        // 评估
        Evaluation eval = new Evaluation(NUM_CLASSES);
        eval.eval(testData.getLabels(), predictions);

        System.out.println("测试集评估结果：");
        System.out.println(eval.stats());

        // 详细指标
        System.out.println("准确率: " + String.format("%.2f%%", eval.accuracy() * 100));
        System.out.println("精确率: " + String.format("%.4f", eval.precision()));
        System.out.println("召回率: " + String.format("%.4f", eval.recall()));
        System.out.println("F1分数: " + String.format("%.4f", eval.f1()));
    }

    /**
     * 分析网络参数
     */
    private static void analyzeNetwork(MultiLayerNetwork model) {
        System.out.println("\n网络参数统计：");
        System.out.println("-".repeat(50));

        long totalParams = model.numParams();
        System.out.println("总参数量: " + totalParams);

        // 分层统计
        for (int i = 0; i < model.getnLayers(); i++) {
            org.deeplearning4j.nn.api.Layer layer = model.getLayer(i);
            String layerName = layer.conf().getLayer().getLayerName();
            long layerParams = layer.numParams();

            if (layerParams > 0) {
                System.out.printf("Layer %d (%s): %d 参数 (%.2f%%)%n",
                    i,
                    layerName != null ? layerName : layer.getClass().getSimpleName(),
                    layerParams,
                    (layerParams * 100.0 / totalParams)
                );
            }
        }

        // 内存估计
        double memoryMB = totalParams * 4.0 / (1024 * 1024);  // 假设 float32
        System.out.printf("\n估计内存占用: %.2f MB%n", memoryMB);
    }
}
