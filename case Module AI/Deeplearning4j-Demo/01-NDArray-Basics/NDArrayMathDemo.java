package com.example.dl4j.ndarray;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

/**
 * NDArray 数学运算示例
 * 
 * 本示例演示：
 * 1. 元素级数学运算
 * 2. 矩阵运算
 * 3. 数学函数
 * 4. 线性代数运算
 * 
 * @author Deeplearning4j Demo
 */
public class NDArrayMathDemo {

    public static void main(String[] args) {
        System.out.println("========== NDArray 数学运算示例 ==========\n");

        // 1. 元素级运算
        demonstrateElementWiseOps();

        // 2. 矩阵运算
        demonstrateMatrixOps();

        // 3. 数学函数
        demonstrateMathFunctions();

        // 4. 线性代数
        demonstrateLinearAlgebra();
    }

    /**
     * 演示元素级运算
     */
    private static void demonstrateElementWiseOps() {
        System.out.println("1. 元素级运算：");
        System.out.println("-".repeat(50));

        INDArray a = Nd4j.create(new double[][]{{1, 2, 3}, {4, 5, 6}});
        INDArray b = Nd4j.create(new double[][]{{2, 2, 2}, {3, 3, 3}});

        System.out.println("数组 a：\n" + a);
        System.out.println("\n数组 b：\n" + b);

        // 加法
        INDArray sum = a.add(b);
        System.out.println("\na + b =\n" + sum);

        // 减法
        INDArray diff = a.sub(b);
        System.out.println("\na - b =\n" + diff);

        // 乘法（元素级）
        INDArray prod = a.mul(b);
        System.out.println("\na * b (元素级) =\n" + prod);

        // 除法
        INDArray div = a.div(b);
        System.out.println("\na / b =\n" + div);

        // 标量运算
        INDArray scalar = a.add(10);
        System.out.println("\na + 10 =\n" + scalar);

        INDArray scaled = a.mul(2);
        System.out.println("\na * 2 =\n" + scaled);

        // In-place 操作（修改原数组，节省内存）
        INDArray c = Nd4j.create(new double[]{1, 2, 3});
        c.addi(5);  // 注意：addi() 会修改原数组
        System.out.println("\nc.addi(5) =\n" + c);

        System.out.println("\n");
    }

    /**
     * 演示矩阵运算
     */
    private static void demonstrateMatrixOps() {
        System.out.println("2. 矩阵运算：");
        System.out.println("-".repeat(50));

        INDArray matrix1 = Nd4j.create(new double[][]{{1, 2}, {3, 4}, {5, 6}});
        INDArray matrix2 = Nd4j.create(new double[][]{{7, 8, 9}, {10, 11, 12}});

        System.out.println("矩阵1 (3x2)：\n" + matrix1);
        System.out.println("\n矩阵2 (2x3)：\n" + matrix2);

        // 矩阵乘法
        INDArray matmul = matrix1.mmul(matrix2);
        System.out.println("\n矩阵乘法 (3x2) × (2x3) = (3x3)：\n" + matmul);

        // 转置
        INDArray transposed = matrix1.transpose();
        System.out.println("\n矩阵1 转置：\n" + transposed);

        // 向量点积
        INDArray vec1 = Nd4j.create(new double[]{1, 2, 3});
        INDArray vec2 = Nd4j.create(new double[]{4, 5, 6});
        double dotProduct = vec1.mul(vec2).sumNumber().doubleValue();
        System.out.println("\n向量点积 [1,2,3] · [4,5,6] = " + dotProduct);

        // 外积
        INDArray outer = vec1.reshape(3, 1).mmul(vec2.reshape(1, 3));
        System.out.println("\n向量外积：\n" + outer);

        System.out.println("\n");
    }

    /**
     * 演示数学函数
     */
    private static void demonstrateMathFunctions() {
        System.out.println("3. 数学函数：");
        System.out.println("-".repeat(50));

        INDArray arr = Nd4j.create(new double[]{-2, -1, 0, 1, 2});
        System.out.println("原始数组：" + arr);

        // 绝对值
        INDArray abs = Transforms.abs(arr);
        System.out.println("\n绝对值：" + abs);

        // 指数函数
        INDArray exp = Transforms.exp(arr);
        System.out.println("exp(x)：" + exp);

        // 对数函数
        INDArray positive = Nd4j.create(new double[]{1, 2, 3, 4, 5});
        INDArray log = Transforms.log(positive);
        System.out.println("\nlog(x)：" + log);

        // 幂函数
        INDArray pow = Transforms.pow(positive, 2);
        System.out.println("x^2：" + pow);

        // 平方根
        INDArray sqrt = Transforms.sqrt(positive);
        System.out.println("√x：" + sqrt);

        // Sigmoid 函数
        INDArray sigmoid = Transforms.sigmoid(arr);
        System.out.println("\nsigmoid(x)：" + sigmoid);

        // Tanh 函数
        INDArray tanh = Transforms.tanh(arr);
        System.out.println("tanh(x)：" + tanh);

        // ReLU 函数
        INDArray relu = Transforms.relu(arr);
        System.out.println("relu(x)：" + relu);

        // 三角函数
        INDArray angles = Nd4j.create(new double[]{0, Math.PI/4, Math.PI/2, Math.PI});
        INDArray sin = Transforms.sin(angles);
        INDArray cos = Transforms.cos(angles);
        System.out.println("\nsin(x)：" + sin);
        System.out.println("cos(x)：" + cos);

        System.out.println("\n");
    }

    /**
     * 演示线性代数运算
     */
    private static void demonstrateLinearAlgebra() {
        System.out.println("4. 线性代数运算：");
        System.out.println("-".repeat(50));

        // 矩阵求逆
        INDArray matrix = Nd4j.create(new double[][]{{4, 7}, {2, 6}});
        System.out.println("原始矩阵：\n" + matrix);
        
        INDArray inverse = org.nd4j.linalg.inverse.InvertMatrix.invert(matrix, false);
        System.out.println("\n逆矩阵：\n" + inverse);
        
        // 验证：A × A^(-1) = I
        INDArray identity = matrix.mmul(inverse);
        System.out.println("\nA × A^(-1) = I：\n" + identity);

        // 矩阵的迹（对角线元素之和）
        INDArray square = Nd4j.create(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        double trace = square.getDouble(0, 0) + square.getDouble(1, 1) + square.getDouble(2, 2);
        System.out.println("\n矩阵的迹：" + trace);

        // 矩阵行列式
        INDArray det = Nd4j.create(new double[][]{{1, 2}, {3, 4}});
        // 注意：DL4J 没有直接的行列式函数，需要通过 LU 分解等方法计算

        // 范数
        INDArray vec = Nd4j.create(new double[]{3, 4});
        double norm1 = vec.norm1Number().doubleValue();  // L1 范数
        double norm2 = vec.norm2Number().doubleValue();  // L2 范数（欧几里得范数）
        System.out.println("\n向量 [3, 4]");
        System.out.println("L1 范数：" + norm1);
        System.out.println("L2 范数：" + norm2);

        System.out.println("\n");
    }
}
