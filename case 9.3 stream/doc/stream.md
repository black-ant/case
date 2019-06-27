## Java 流操作

#### 什么是流 ? 
1 .  Stream  不是集合元素 , 不是数据结构 , 不保存数据
2 .  Stream  用于算法和计算 , 类似于高级 的 Iterator
3 .  不同于迭代器 , Stream 可以并行化操作 , 数据被分为很多段 , 在不同的线程中进行处理

#### 流的构成
1 .  获取数据源 
2 .  数据转换
3 .  执行操作获取想要的结果

#### 基础点
###### 双冒号运算

```
双冒号运算就是将方法当成参数传递给需要的方法 ( Stream ) , 即为方法引用 , 
类名 : : 方法名
```



###### 选择器
```java
nums.stream() .filter(num -> num % 2 == 0)
// 此处的num 类似于下面for 循环中的num 
for (final Integer num : nums) {
    if (num % 2 == 0) { }
}
```




#### 获取流的方式
###### Collection 和 数组
1 .  Collection.stream () ---  list.stream();
2 .  Collection.parallelStream ()
3 .  Arrays.stream( T array ) --- Arrays.stream(strArray);
4 .  Stream.of ( )  --- Stream.of(strArray);
5 .  java.io.BufferedReader.lines()

###### 静态工厂
1 .  Java.util.stream.IntStream.range ( )
2 .  Java.nio.file.Files.walk ( )

###### 自己构建
1 .   java.util.Spliterator
2 .  Random.ints()
3 .  BitSet.stream()
4 .  Pattern.splitAsStream(java.lang.CharSequence)
5 .  JarFile.stream()

#### 流的操作类型
1  .  Intermediate : 一个流后可以跟随零个或多个 intermediate 操作 . 

此时仅仅是打开流 , 对流进行过滤和映射 , 然后返回新流 
此时的操作均为惰性 , 仅仅是调用 , 并没有开始遍历

2  .  Terminal : 一个流只能有一个Terminal  操作 , 操作执行后 , 流即被使用完 ,是流的最后操作 . 此后 , 开始流的遍历 , 


#### 流的使用
IntStream.range(1, 3).forEach(System.out::println);


#### 流的转换
###### Array
 stream.toArray(String[]::new);

###### collection



```java
// toList : 把流中所有的元素收集到List 中
stream.collect(Collectors.toList());

// toCollection : 把流中的元素收集到给定的供应源创建的集合中
 stream.collect(Collectors.toCollection(ArrayList::new));

// toSet : 把流中所有的元素保存到Set集合中,删除重复锁
stream.collect(Collectors.toSet());

stream.collect(Collectors.toCollection(Stack::new));

// stream 数组转换
String[] testStrings = { "java", "react", "angular", "vue" };
List<String> list = Stream.of(testStrings).collect(Collectors.toList());

```



###### String 
stream.collect(Collectors.joining()).toString();
list.stream().collect(Collectors.joining(" | ")) === 连接符中间穿插
list.stream().collect(Collectors.joining(" || ", "Start--", "--End")) === 连接符中间及前后

#### 简单梳理
获取的流可以看成一个隐式高级迭代器 , 通过 filter 时 定义的变量即为迭代时的对象 , 


#### 流的操作

###### Intermediate 操作

```java
> map (mapToInt, 等) 
> ------ > List<String> output = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());

> ------ ? 将所有的单词转换大小写
> ------ > nums . stream() . map( n -> n * n ) . collect ( Collectors.toList() );
> ------ ? 求平方数

> flatMap : 将多个层级扁平化 ， 将多个stream 融合为一个stream
> ------ > inputStream.flatMap ( (childList ) -> childList.stream () );
> ------ ? 求平方数

> filter : 过滤 ,通过过滤的元素被流下来生成新的 stream
> ------ > Stream<T> filter(Predicate<? super T> predicate) === Predicate 是一个函数式接口
> ------ > Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);

// 扁平化后过滤
 reader.lines().flatMap( line -> Stream.of ( line.split(REGEXP) ) ).filter(word -> word.length() > 0).collect(Collectors.toList());
// 过滤字符串数组
list.stream().filter(p -> p.startsWith("j")).count();

> distinct : distinct基于Object.equals(Object)实现 ,用于去重
> ------- > nums.stream() .filter(num -> num % 2 == 0) . distinct() . collect(Collectors.toList());

> sorted : 对Stream 进行 sorted 进行排序 , 可先通过 map、filter、limit、skip等赛选数量后排序
> ------- > persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
```

peek
```
limit : limit 返回 Stream 的前面 n 个元素
------- > nums. stream() .filter(n-> n>2 ).limit(2) . collect( Collectors . toList () );

skip :  扔掉前 n 个元素
------- > nums. stream() .filter(n-> n>2 ).skip (2) . collect( Collectors . toList () );
parallel
sequential
unordered
```



###### Terminal 操作



```
forEach : 
------- > roster.stream().filter ( p -> p.getGender() == Person.Sex.MALE) . forEach ( p -> System.out.println ( p.getName() ) );
```

forEachOrdered

toArray

```java
reduce : 把 Stream 元素组合起来
-------- >  Stream.of("A", "B", "C", "D").reduce("", String::concat);
? -------- reduce 后面的参数 : 第一个表示开始的位置 , 后面是传入的方法
collect
// min : 将字符串数组求最大值
Stream.of(testStrings).max((p1, p2) -> Integer.compare(p1.length(), p2.length()));
// max : 获得最大长度
br.lines().mapToInt(String::length).max().getAsInt();
```

count 
anyMatch
allMatch
noneMatch
findFirst : findFirst用于返回满足条件的第一个元素
-------- > students.stream().filter(student ->student .getage()>20 ).findFirst();

findAny : findAny相对于findFirst的区别在于，findAny不一定返回第一个，而是返回任意一个

iterator

###### Short-circuiting 
> anyMatch : Stream 中只要有一个元素符合传入的 predicate，返回 true
> ? ------- persons.stream(). anyMatch(p -> p.getAge() < 12);

> allMatch : Stream 中全部元素符合传入的 predicate，返回 true
> ? ------- persons.stream(). allMatch(p -> p.getAge() > 18);

> noneMatch :  Stream 中没有一个元素符合传入的 predicate，返回 true 


> findFirst : 返回 Stream 的第一个元素
> findAnt


###### 规约操作
```java
reduce : 对参数化操作后的集合进行进一步操作
students.stream() .filter(student -> "计算机科学".equals(student.getMajor())) .map(Student::getAge) .reduce(0, (a, b) -> a + b);

students.stream() .filter(student -> "计算机科学".equals(student.getMajor())) .map(Student::getAge) .reduce(0, Integer::sum);

students.stream() .filter(student -> "计算机科学".equals(student.getMajor())) .map(Student::getAge) .reduce(Integer::sum); 
```



```java
> GROUP BY : 该关键字用于分组操作

// 单级分组
> students.stream().collect(Collectors.groupingBy(Student::getSchool))

// 多级分组
> students.stream().collect(
>         Collectors.groupingBy(Student::getSchool,  // 一级分组，按学校
>         Collectors.groupingBy(Student::getMajor)));  // 二级分组，按专业

// partitioningBy : 分区 ,区别于groupBy ,分区中只有true , false .
> students.stream().collect(Collectors.partitioningBy(student -> "武汉大学".equals(student.getSchool())));
```


##### 案例 一 : 对 List AList 中 每个 元素进行操作转换后生成 另外的类型 C 放入 List<C> BList 

```java
List<JSONObject> itemjson = new LinkedList<JSONObject>();
List<A> aList = ... 
itemCW.stream().map(c -> {
            JSONObject nodeitem = new JSONObject();
            nodeitem.put("whtype", 0);
            return nodeitem;
}).forEach(c -> itemjson.add(c));
```

##### 案例二 ： 对 Map 进行循环操作

```java
       realmTO.getTemplates().forEach((key, template) -> {
            AnyType type = anyTypeDAO.find(key);
            anyTemplate.set(template);
        });
// for-each  的时候 ， 可以传入 key 和 对象 ，后续可以使用
```



##### 案例三 ： 



流操作性能分析 :
1 .  排序
2 . 去重
3 . 筛选
4 . 获取部分信息
5 . 判断是否包含
6 . 均满足
7 . 求和
8 . 分组
9 . 链式



https://www.cnblogs.com/shenlanzhizun/p/6027042.html
https://segmentfault.com/a/1190000004171551?utm_source=tag-newest
https://www.cnblogs.com/Dorae/p/7779246.html
https://www.cnblogs.com/cjsblog/p/8992048.html
https://www.jianshu.com/p/9fe8632d0bc2
流操作源码解读
http://www.importnew.com/26090.html