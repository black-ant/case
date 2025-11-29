# NDArray 基础操作

## 概述

ND4J (N-Dimensional Arrays for Java) 是 Deeplearning4j 的数值计算引擎，类似于 Python 的 NumPy。NDArray 是 ND4J 中的核心数据结构，用于表示多维数组。

## 为什么需要 NDArray？

- **高性能**：底层使用 C++ 实现，支持 CPU 和 GPU 加速
- **多维支持**：支持任意维度的数组操作
- **广播机制**：自动处理不同形状数组的运算
- **内存优化**：高效的内存管理和操作

## 核心概念

### 1. NDArray 的创建

NDArray 提供多种创建方式：

```java
// 从数组创建
INDArray arr = Nd4j.create(new double[]{1, 2, 3, 4});

// 创建零矩阵
INDArray zeros = Nd4j.zeros(3, 4);

// 创建单位矩阵
INDArray ones = Nd4j.ones(3, 4);

// 创建随机矩阵
INDArray random = Nd4j.rand(3, 4);

// 创建指定形状
INDArray shaped = Nd4j.create(new int[]{2, 3, 4});
```

### 2. 形状 (Shape)

形状定义了数组的维度：

- **标量**：形状 []，如 5
- **向量**：形状 [n]，如 [1, 2, 3]
- **矩阵**：形状 [m, n]，如 2x3 矩阵
- **张量**：形状 [d1, d2, ..., dn]，如 2x3x4 三维数组

```java
INDArray arr = Nd4j.create(2, 3, 4);
System.out.println("Shape: " + Arrays.toString(arr.shape()));
// 输出: Shape: [2, 3, 4]
```

### 3. 基本数学运算

#### 元素级运算
```java
INDArray a = Nd4j.create(new double[]{1, 2, 3});
INDArray b = Nd4j.create(new double[]{4, 5, 6});

// 加法
INDArray sum = a.add(b);  // [5, 7, 9]

// 减法
INDArray diff = a.sub(b); // [-3, -3, -3]

// 乘法（元素级）
INDArray prod = a.mul(b); // [4, 10, 18]

// 除法
INDArray div = a.div(b);  // [0.25, 0.4, 0.5]
```

#### 矩阵运算
```java
INDArray matrix1 = Nd4j.create(new double[][]{{1, 2}, {3, 4}});
INDArray matrix2 = Nd4j.create(new double[][]{{5, 6}, {7, 8}});

// 矩阵乘法
INDArray result = matrix1.mmul(matrix2);

// 转置
INDArray transposed = matrix1.transpose();
```

### 4. 索引与切片

```java
INDArray arr = Nd4j.create(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});

// 获取元素
double value = arr.getDouble(0, 1); // 2.0

// 获取行
INDArray row = arr.getRow(1); // [4, 5, 6]

// 获取列
INDArray col = arr.getColumn(2); // [3, 6, 9]

// 切片
INDArray slice = arr.get(NDArrayIndex.interval(0, 2), NDArrayIndex.all());
// [[1, 2, 3], [4, 5, 6]]
```

### 5. 广播机制

广播允许不同形状的数组进行运算：

```java
INDArray matrix = Nd4j.create(new double[][]{{1, 2, 3}, {4, 5, 6}});
INDArray vector = Nd4j.create(new double[]{10, 20, 30});

// 广播加法：每行都加上 vector
INDArray result = matrix.addRowVector(vector);
// [[11, 22, 33], [14, 25, 36]]
```

### 6. 聚合操作

```java
INDArray arr = Nd4j.create(new double[][]{{1, 2, 3}, {4, 5, 6}});

// 求和
double sum = arr.sumNumber().doubleValue(); // 21.0

// 平均值
double mean = arr.meanNumber().doubleValue(); // 3.5

// 最大值
double max = arr.maxNumber().doubleValue(); // 6.0

// 最小值
double min = arr.minNumber().doubleValue(); // 1.0

// 沿轴操作
INDArray columnSum = arr.sum(0); // [5, 7, 9]
INDArray rowSum = arr.sum(1);    // [6, 15]
```

### 7. 形状变换

```java
INDArray arr = Nd4j.create(new double[]{1, 2, 3, 4, 5, 6});

// Reshape
INDArray reshaped = arr.reshape(2, 3);
// [[1, 2, 3], [4, 5, 6]]

// Flatten
INDArray flattened = reshaped.ravel();
// [1, 2, 3, 4, 5, 6]

// 转置
INDArray transposed = reshaped.transpose();
// [[1, 4], [2, 5], [3, 6]]
```

## 示例代码说明

### NDArrayBasicsDemo.java
演示 NDArray 的基本创建和操作方法。

### NDArrayMathDemo.java
展示各种数学运算，包括元素级和矩阵运算。

### NDArrayIndexingDemo.java
演示索引、切片和高级索引技术。

### NDArrayBroadcastingDemo.java
解释和演示广播机制的使用。

### NDArrayAggregationDemo.java
展示各种聚合操作和统计函数。

## 最佳实践

1. **内存管理**
   - 使用完 NDArray 后及时释放：`arr.close()` 或使用 try-with-resources
   - 注意 in-place 操作（如 `addi()`）可以节省内存

2. **性能优化**
   - 使用向量化操作而不是循环
   - 尽量使用 in-place 操作减少内存分配
   - 合理使用数据类型（float vs double）

3. **形状注意事项**
   - 始终注意数组的形状和维度
   - 使用 `shape()` 方法检查形状
   - 注意广播规则

4. **调试技巧**
   - 使用 `arr.shapeInfoToString()` 查看详细信息
   - 使用 `System.out.println(arr)` 打印数组内容
   - 使用断言检查形状和值

## 常见问题

**Q: NDArray 和 Java 数组的区别？**
A: NDArray 提供更丰富的操作、更好的性能、GPU 支持，以及自动的内存管理。

**Q: 如何选择数据类型？**
A: 一般使用 float 即可（节省内存），需要高精度时使用 double。

**Q: 广播失败怎么办？**
A: 检查数组形状是否满足广播规则，必要时使用 `reshape()` 或 `broadcast()` 方法。

## 相关资源

- [ND4J 官方文档](https://deeplearning4j.konduit.ai/nd4j/overview)
- [ND4J API 文档](https://javadoc.io/doc/org.nd4j/nd4j-api/latest/index.html)
- NumPy 用户可参考 [ND4J for NumPy Users](https://deeplearning4j.konduit.ai/nd4j/tutorials/numpy)

## 下一步

学习完 NDArray 基础后，继续学习：
- [数据预处理](../02-Data-Preprocessing/) - 学习如何准备训练数据
- [全连接神经网络](../03-Fully-Connected-Network/) - 开始构建神经网络
