# PageHelper 源码分析

## 一  整体结构

```
> PageHelper
	I- Constant
	I- Dialect : 数据库方言接口 , 包括一些常见得方法
		M- Skip : 跳过Count 和 分页
		M- beforeCount : 分页前判断
		M- getCountSql : 生成 count 查询 sql
		M- afterCount : 执行完 count 查询后
		M- processParameterObject : 处理查询参数对象
		M- beforePage : 执行分页前，返回 true 会进行分页查询，false 会返回默认查询结果
		M- getPageSql : 成分页查询 sql
		M- afterPage : 分页查询后，处理分页结果，拦截器中直接 return 该方法的返回值
		M- afterAll : 完成所有任务后
		M- setProperties : 设置参数
	I- IPage
	I- ISelect
	C- Page
	C- PageException
	C- PageHelper
	C- PageInfo
	C- PageInterceptor : 核心拦截器 , 见下文详解
	C- PageRowBounds
	C- PageSerializable
		P- total
		P- List
		CM- PageSerializable(List<T> list)
			-> 如果是 Page 对象 , 获取 getTotal
			-> 如果不是 ,  list.size()
		M- of : 转换为 PageSerializable
	C- QueryInterceptor : 查询过滤器
	
> cache
	I- Cache : 
		M- get(K key)
		M- put(K key, V value)
	C- CacheFactory
    	M- createCache
    	
```



## 二  关键类

### 2 . 1 PageInterceptor

```
> PageInterceptor : 核心拦截器 
	I- org.apache.ibatis.plugin.Interceptor : 注意此处是ibatis plugin
	P- Dialect : 每种数据库得具体实现
	P- String default_dialect_class : 默认处理类
	M- intercept
	M- checkDialectExists : 检测方言是否存在
	M- count
	M- plugin
	M- setProperties
	
	
```

### 2 . 2 QueryInterceptor

```

```

