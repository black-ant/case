package com.example.dl4j.preprocessing;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.List;

/**
 * DataSet 基础操作示例
 * 
 * 本示例演示：
 * 1. DataSet 的创建
 * 2. 特征和标签的访问
 * 3. 数据集划分
 * 4. 批处理操作
 * 
 * @author Deeplearning4j Demo
 */
public class DataSetBasicsDemo {

    public static void main(String[] args) {
        System.out.println("========== DataSet 基础操作示例 ==========\n");

        // 1. 创建 DataSet
        demonstrateDataSetCreation();

        // 2. 访问 DataSet
        demonstrateDataSetAccess();

        // 3. 数据集划分
        demonstrateDataSplit();

        // 4. 数据合并
        demonstrateDataMerge();
    }

    /**
     * 演示 DataSet 创建
     */
    private static void demonstrateDataSetCreation() {
        System.out.println("1. DataSet 创建：");
        System.out.println("-".repeat(50));

        // 创建特征矩阵（4个样本，3个特征）
        INDArray features = Nd4j.create(new double[][]{
            {1.0, 2.0, 3.0},
            {4.0, 5.0, 6.0},
            {7.0, 8.0, 9.0},
            {10.0, 11.0, 12.0}
        });

        // 创建标签（4个样本，2个类别 - one-hot 编码）
        INDArray labels = Nd4j.create(new double[][]{
            {1, 0},  // 类别 0
            {0, 1},  // 类别 1
            {1, 0},  // 类别 0
            {0, 1}   // 类别 1
        });

        // 创建 DataSet
        DataSet dataSet = new DataSet(features, labels);

        System.out.println("DataSet 创建成功！");
        System.out.println("样本数量：" + dataSet.numExamples());
        System.out.println("特征维度：" + Arrays.toString(dataSet.getFeatures().shape()));
        System.out.println("标签维度：" + Arrays.toString(dataSet.getLabels().shape()));

        System.out.println("\n特征矩阵：\n" + dataSet.getFeatures());
        System.out.println("\n标签矩阵：\n" + dataSet.getLabels());

        System.out.println("\n");
    }

    /**
     * 演示 DataSet 访问
     */
    private static void demonstrateDataSetAccess() {
        System.out.println("2. DataSet 访问：");
        System.out.println("-".repeat(50));

        // 创建数据集
        INDArray features = Nd4j.rand(5, 4);  // 5个样本，4个特征
        INDArray labels = Nd4j.create(new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0},
            {0, 1, 0}
        });

        DataSet dataSet = new DataSet(features, labels);

        // 获取特定样本
        DataSet firstSample = dataSet.get(0);
        System.out.println("第一个样本的特征：\n" + firstSample.getFeatures());
        System.out.println("\n第一个样本的标签：\n" + firstSample.getLabels());

        // 获取一批样本
        List<DataSet> batch = dataSet.asList();
        System.out.println("\n批次数量：" + batch.size());

        // 遍历数据集
        System.out.println("\n遍历所有样本：");
        for (int i = 0; i < dataSet.numExamples(); i++) {
            DataSet sample = dataSet.get(i);
            System.out.println("样本 " + i + " 特征：" + sample.getFeatures());
        }

        // 打乱数据
        dataSet.shuffle();
        System.out.println("\n数据已打乱");

        System.out.println("\n");
    }

    /**
     * 演示数据集划分
     */
    private static void demonstrateDataSplit() {
        System.out.println("3. 数据集划分：");
        System.out.println("-".repeat(50));

        // 创建数据集
        INDArray features = Nd4j.rand(100, 10);  // 100个样本，10个特征
        INDArray labels = Nd4j.rand(100, 3);     // 3个类别

        DataSet dataSet = new DataSet(features, labels);

        System.out.println("原始数据集大小：" + dataSet.numExamples());

        // 划分训练集和测试集（80% 训练，20% 测试）
        SplitTestAndTrain split = dataSet.splitTestAndTrain(0.8);
        DataSet trainData = split.getTrain();
        DataSet testData = split.getTest();

        System.out.println("\n训练集大小：" + trainData.numExamples());
        System.out.println("测试集大小：" + testData.numExamples());

        // 进一步划分验证集
        SplitTestAndTrain trainValidSplit = trainData.splitTestAndTrain(0.875);  // 70% 训练，10% 验证
        DataSet finalTrainData = trainValidSplit.getTrain();
        DataSet validData = trainValidSplit.getTest();

        System.out.println("\n最终训练集大小：" + finalTrainData.numExamples());
        System.out.println("验证集大小：" + validData.numExamples());
        System.out.println("测试集大小：" + testData.numExamples());

        // 验证比例
        int total = finalTrainData.numExamples() + validData.numExamples() + testData.numExamples();
        System.out.println("\n训练集占比：" + (finalTrainData.numExamples() * 100.0 / total) + "%");
        System.out.println("验证集占比：" + (validData.numExamples() * 100.0 / total) + "%");
        System.out.println("测试集占比：" + (testData.numExamples() * 100.0 / total) + "%");

        System.out.println("\n");
    }

    /**
     * 演示数据合并
     */
    private static void demonstrateDataMerge() {
        System.out.println("4. 数据合并：");
        System.out.println("-".repeat(50));

        // 创建两个数据集
        DataSet dataSet1 = new DataSet(
            Nd4j.rand(10, 5),
            Nd4j.rand(10, 2)
        );

        DataSet dataSet2 = new DataSet(
            Nd4j.rand(15, 5),
            Nd4j.rand(15, 2)
        );

        System.out.println("数据集1大小：" + dataSet1.numExamples());
        System.out.println("数据集2大小：" + dataSet2.numExamples());

        // 合并数据集
        dataSet1.merge(dataSet2);

        System.out.println("\n合并后数据集大小：" + dataSet1.numExamples());

        // 使用 List 合并多个数据集
        DataSet ds1 = new DataSet(Nd4j.rand(5, 3), Nd4j.rand(5, 2));
        DataSet ds2 = new DataSet(Nd4j.rand(8, 3), Nd4j.rand(8, 2));
        DataSet ds3 = new DataSet(Nd4j.rand(12, 3), Nd4j.rand(12, 2));

        DataSet merged = DataSet.merge(Arrays.asList(ds1, ds2, ds3));

        System.out.println("\n多个数据集合并：");
        System.out.println("ds1: " + ds1.numExamples() + " 样本");
        System.out.println("ds2: " + ds2.numExamples() + " 样本");
        System.out.println("ds3: " + ds3.numExamples() + " 样本");
        System.out.println("合并后: " + merged.numExamples() + " 样本");

        System.out.println("\n");
    }
}
