package com.example.dl4j.ndarray;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

/**
 * NDArray 基础操作示例
 * 
 * 本示例演示：
 * 1. NDArray 的多种创建方式
 * 2. 查看和理解 NDArray 的形状
 * 3. 基本的数组操作
 * 
 * @author Deeplearning4j Demo
 */
public class NDArrayBasicsDemo {

    public static void main(String[] args) {
        System.out.println("========== NDArray 基础操作示例 ==========\n");

        // 1. 创建 NDArray
        demonstrateCreation();

        // 2. 形状操作
        demonstrateShape();

        // 3. 基本操作
        demonstrateBasicOperations();

        // 4. 数据类型
        demonstrateDataTypes();
    }

    /**
     * 演示 NDArray 的多种创建方式
     */
    private static void demonstrateCreation() {
        System.out.println("1. NDArray 创建方式：");
        System.out.println("-".repeat(50));

        // 从一维数组创建
        INDArray vector = Nd4j.create(new double[]{1, 2, 3, 4, 5});
        System.out.println("从一维数组创建：\n" + vector);

        // 从二维数组创建
        INDArray matrix = Nd4j.create(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });
        System.out.println("\n从二维数组创建：\n" + matrix);

        // 创建全零数组
        INDArray zeros = Nd4j.zeros(3, 4);
        System.out.println("\n全零数组 (3x4)：\n" + zeros);

        // 创建全一数组
        INDArray ones = Nd4j.ones(2, 3);
        System.out.println("\n全一数组 (2x3)：\n" + ones);

        // 创建随机数组（0-1之间的均匀分布）
        INDArray random = Nd4j.rand(2, 3);
        System.out.println("\n随机数组 (2x3)：\n" + random);

        // 创建正态分布随机数组（均值0，标准差1）
        INDArray randn = Nd4j.randn(2, 3);
        System.out.println("\n正态分布随机数组 (2x3)：\n" + randn);

        // 创建单位矩阵
        INDArray eye = Nd4j.eye(4);
        System.out.println("\n单位矩阵 (4x4)：\n" + eye);

        // 创建指定范围的数组
        INDArray range = Nd4j.arange(0, 10);
        System.out.println("\n范围数组 [0, 10)：\n" + range);

        // 创建等间距数组
        INDArray linspace = Nd4j.linspace(0, 1, 5);
        System.out.println("\n等间距数组 [0, 1] 5个点：\n" + linspace);

        System.out.println("\n");
    }

    /**
     * 演示形状相关操作
     */
    private static void demonstrateShape() {
        System.out.println("2. 形状操作：");
        System.out.println("-".repeat(50));

        INDArray arr = Nd4j.create(new double[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        });

        System.out.println("原始数组：\n" + arr);
        
        // 获取形状
        long[] shape = arr.shape();
        System.out.println("\n形状：" + Arrays.toString(shape));
        
        // 获取维度数
        int rank = arr.rank();
        System.out.println("维度数（秩）：" + rank);
        
        // 获取元素总数
        long size = arr.length();
        System.out.println("元素总数：" + size);
        
        // 获取行数和列数
        long rows = arr.rows();
        long cols = arr.columns();
        System.out.println("行数：" + rows + ", 列数：" + cols);

        // Reshape - 改变形状
        INDArray reshaped = arr.reshape(4, 3);
        System.out.println("\nReshape 为 (4x3)：\n" + reshaped);

        // Transpose - 转置
        INDArray transposed = arr.transpose();
        System.out.println("\n转置后：\n" + transposed);

        // Flatten - 展平为一维
        INDArray flattened = arr.ravel();
        System.out.println("\n展平为一维：\n" + flattened);

        // 三维数组示例
        INDArray tensor3d = Nd4j.create(2, 3, 4);
        System.out.println("\n三维张量形状：" + Arrays.toString(tensor3d.shape()));

        System.out.println("\n");
    }

    /**
     * 演示基本操作
     */
    private static void demonstrateBasicOperations() {
        System.out.println("3. 基本操作：");
        System.out.println("-".repeat(50));

        INDArray arr = Nd4j.create(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        System.out.println("原始数组：\n" + arr);

        // 获取单个元素
        double element = arr.getDouble(1, 2);
        System.out.println("\n元素 arr[1][2] = " + element);

        // 修改单个元素
        arr.putScalar(new int[]{0, 0}, 10);
        System.out.println("\n修改 arr[0][0] = 10 后：\n" + arr);

        // 获取行
        INDArray row1 = arr.getRow(1);
        System.out.println("\n第2行：" + row1);

        // 获取列
        INDArray col2 = arr.getColumn(2);
        System.out.println("第3列：" + col2);

        // 复制数组
        INDArray copy = arr.dup();
        System.out.println("\n复制的数组：\n" + copy);

        // 填充数组
        INDArray filled = Nd4j.zeros(2, 3);
        filled.assign(7.0);
        System.out.println("\n填充值为7的数组：\n" + filled);

        System.out.println("\n");
    }

    /**
     * 演示不同数据类型
     */
    private static void demonstrateDataTypes() {
        System.out.println("4. 数据类型：");
        System.out.println("-".repeat(50));

        // Double 类型（默认）
        INDArray doubleArr = Nd4j.create(new double[]{1.1, 2.2, 3.3});
        System.out.println("Double 数组：" + doubleArr);
        System.out.println("数据类型：" + doubleArr.dataType());

        // Float 类型
        INDArray floatArr = Nd4j.create(new float[]{1.1f, 2.2f, 3.3f});
        System.out.println("\nFloat 数组：" + floatArr);
        System.out.println("数据类型：" + floatArr.dataType());

        // Integer 类型
        INDArray intArr = Nd4j.create(new int[]{1, 2, 3});
        System.out.println("\nInteger 数组：" + intArr);
        System.out.println("数据类型：" + intArr.dataType());

        // Long 类型
        INDArray longArr = Nd4j.create(new long[]{1L, 2L, 3L});
        System.out.println("\nLong 数组：" + longArr);
        System.out.println("数据类型：" + longArr.dataType());

        // 类型转换
        INDArray converted = doubleArr.castTo(org.nd4j.linalg.api.buffer.DataType.FLOAT);
        System.out.println("\n转换为 Float：" + converted);
        System.out.println("数据类型：" + converted.dataType());

        System.out.println("\n");
    }
}
