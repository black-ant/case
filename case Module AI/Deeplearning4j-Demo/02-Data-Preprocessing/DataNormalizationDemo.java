package com.example.dl4j.preprocessing;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.dataset.api.preprocessor.serializer.NormalizerSerializer;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.util.Arrays;

/**
 * 数据归一化示例
 * 
 * 演示不同的数据归一化技术：
 * 1. Min-Max 归一化
 * 2. 标准化（Z-score）
 * 3. 归一化器的保存和加载
 * 
 * @author Deeplearning4j Demo
 */
public class DataNormalizationDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("========== 数据归一化示例 ==========\n");

        // 1. 创建示例数据
        DataSet dataset = createSampleDataSet();
        
        // 2. Min-Max 归一化
        demonstrateMinMaxNormalization(dataset.copy());
        
        // 3. 标准化
        demonstrateStandardization(dataset.copy());
        
        // 4. 归一化器的保存和加载
        demonstrateNormalizerPersistence(dataset.copy());
        
        // 5. 反归一化
        demonstrateDenormalization(dataset.copy());
    }

    /**
     * 创建示例数据集
     */
    private static DataSet createSampleDataSet() {
        // 创建特征矩阵（5个样本，3个特征）
        INDArray features = Nd4j.create(new double[][]{
            {10.0, 200.0, 0.5},
            {20.0, 150.0, 1.5},
            {15.0, 180.0, 1.0},
            {25.0, 220.0, 2.0},
            {30.0, 250.0, 2.5}
        });

        // 创建标签
        INDArray labels = Nd4j.create(new double[][]{
            {1, 0},
            {0, 1},
            {1, 0},
            {0, 1},
            {1, 0}
        });

        return new DataSet(features, labels);
    }

    /**
     * 演示 Min-Max 归一化
     */
    private static void demonstrateMinMaxNormalization(DataSet dataset) {
        System.out.println("1. Min-Max 归一化 [0, 1]");
        System.out.println("-".repeat(50));

        System.out.println("原始数据：");
        System.out.println(dataset.getFeatures());
        printStatistics(dataset.getFeatures());

        // 创建 Min-Max 归一化器（范围 0-1）
        NormalizerMinMaxScaler scaler = new NormalizerMinMaxScaler(0, 1);

        // 拟合归一化器
        scaler.fit(dataset);

        // 应用归一化
        scaler.transform(dataset);

        System.out.println("\n归一化后的数据：");
        System.out.println(dataset.getFeatures());
        printStatistics(dataset.getFeatures());

        // 也可以归一化到其他范围，如 [-1, 1]
        DataSet dataset2 = createSampleDataSet();
        NormalizerMinMaxScaler scaler2 = new NormalizerMinMaxScaler(-1, 1);
        scaler2.fit(dataset2);
        scaler2.transform(dataset2);

        System.out.println("\n归一化到 [-1, 1]：");
        System.out.println(dataset2.getFeatures());

        System.out.println("\n");
    }

    /**
     * 演示标准化（Z-score）
     */
    private static void demonstrateStandardization(DataSet dataset) {
        System.out.println("2. 标准化（Z-score）");
        System.out.println("-".repeat(50));

        System.out.println("原始数据：");
        System.out.println(dataset.getFeatures());
        printStatistics(dataset.getFeatures());

        // 创建标准化器
        NormalizerStandardize normalizer = new NormalizerStandardize();

        // 拟合归一化器（计算均值和标准差）
        normalizer.fit(dataset);

        // 显示计算的统计信息
        System.out.println("\n计算的均值：");
        System.out.println(normalizer.getMean());

        System.out.println("\n计算的标准差：");
        System.out.println(normalizer.getStd());

        // 应用标准化
        normalizer.transform(dataset);

        System.out.println("\n标准化后的数据（均值≈0，标准差≈1）：");
        System.out.println(dataset.getFeatures());
        printStatistics(dataset.getFeatures());

        System.out.println("\n");
    }

    /**
     * 演示归一化器的保存和加载
     */
    private static void demonstrateNormalizerPersistence(DataSet dataset) throws Exception {
        System.out.println("3. 归一化器的保存和加载");
        System.out.println("-".repeat(50));

        // 创建并训练归一化器
        NormalizerStandardize normalizer = new NormalizerStandardize();
        normalizer.fit(dataset);

        // 保存归一化器
        File normalizerFile = new File("normalizer.bin");
        NormalizerSerializer.getDefault().write(normalizer, normalizerFile);
        System.out.println("✓ 归一化器已保存到: " + normalizerFile.getAbsolutePath());

        // 创建新的数据集（模拟新数据）
        INDArray newFeatures = Nd4j.create(new double[][]{
            {12.0, 190.0, 0.8},
            {28.0, 240.0, 2.2}
        });
        DataSet newDataset = new DataSet(newFeatures, Nd4j.zeros(2, 2));

        System.out.println("\n新数据（归一化前）：");
        System.out.println(newDataset.getFeatures());

        // 加载归一化器
        NormalizerStandardize loadedNormalizer = 
            NormalizerSerializer.getDefault().restore(normalizerFile);
        System.out.println("\n✓ 归一化器已加载");

        // 应用到新数据
        loadedNormalizer.transform(newDataset);

        System.out.println("\n新数据（归一化后）：");
        System.out.println(newDataset.getFeatures());

        System.out.println("\n");
    }

    /**
     * 演示反归一化
     */
    private static void demonstrateDenormalization(DataSet dataset) {
        System.out.println("4. 反归一化");
        System.out.println("-".repeat(50));

        // 保存原始数据
        INDArray originalFeatures = dataset.getFeatures().dup();

        System.out.println("原始数据：");
        System.out.println(originalFeatures);

        // 创建并应用归一化
        NormalizerStandardize normalizer = new NormalizerStandardize();
        normalizer.fit(dataset);
        normalizer.transform(dataset);

        System.out.println("\n归一化后：");
        System.out.println(dataset.getFeatures());

        // 反归一化
        normalizer.revert(dataset);

        System.out.println("\n反归一化后：");
        System.out.println(dataset.getFeatures());

        // 验证是否恢复到原始数据
        INDArray diff = originalFeatures.sub(dataset.getFeatures());
        double maxDiff = diff.abs().maxNumber().doubleValue();

        System.out.println("\n与原始数据的最大差异: " + maxDiff);
        if (maxDiff < 1e-6) {
            System.out.println("✓ 完美恢复！");
        }

        System.out.println("\n");
    }

    /**
     * 打印数据统计信息
     */
    private static void printStatistics(INDArray data) {
        // 计算每列的统计信息
        int numFeatures = (int) data.size(1);

        System.out.println("\n统计信息：");
        System.out.println("特征 | 最小值 | 最大值 | 均值   | 标准差");
        System.out.println("-".repeat(50));

        for (int i = 0; i < numFeatures; i++) {
            INDArray column = data.getColumn(i);
            double min = column.minNumber().doubleValue();
            double max = column.maxNumber().doubleValue();
            double mean = column.meanNumber().doubleValue();
            double std = column.stdNumber().doubleValue();

            System.out.printf("  %d  | %6.2f | %6.2f | %6.2f | %6.2f%n",
                i, min, max, mean, std);
        }
    }
}
