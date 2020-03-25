# SpringBoot SpringFactoriesLoader

## SpringFactoriesLoader 

```
SpringFactoriesLoader  : 加载 spring.factories 的工具类
> 1 内部使用的通用工厂加载机制。
> 2 loadFactories加载 并实例化

P- FACTORIES_RESOURCE_LOCATION : 
	-> 静态属性，定义了读取的是 "META-INF/spring.factories" 配置文件
	
P- Map<ClassLoader, MultiValueMap<String, String>> cache 
	-> 读取 "META-INF/spring.factories" 配置文件的缓存
	
M- loadFactories
	-> classLoaderToUse = SpringFactoriesLoader.class.getClassLoader();
	-> loadFactoryNames(factoryClass, classLoaderToUse);
		?- 获得接口对应的实现类名们
	-> new ArrayList<>(factoryNames.size());
	FOR->  遍历 factoryNames 数组，创建实现类的对象
		-> result.add(instantiateFactory(factoryName, factoryClass, classLoaderToUse));
	-> AnnotationAwareOrderComparator.sort(result); -- 排序
	
M- loadFactoryNames : 静态方法，获得接口对应的实现类名们
	-> factoryClass.getName(); -- 获得接口的类名
	-> loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList())
		?- 加载 FACTORIES_RESOURCE_LOCATION 配置文件，获得接口对应的实现类名们
		
M- loadSpringFactories
	-> MultiValueMap<String, String> result = cache.get(classLoader);
		?- 如果缓存中已经存在，则直接返回
	-> 获得 FACTORIES_RESOURCE_LOCATION 对应的 URL 们
    -> 创建 LinkedMultiValueMap 对象
    FOR-> 遍历 URL 数组
    	-> 获得 URL
    	-> 创建 UrlResource 对象
    	-> 加载 "META-INF/spring.factories" 配置文件，成为 Properties 对象
    	FOR-> 遍历 Properties 对象
    		-> 使用逗号分隔
    		-> 添加到 result 中
    -> 添加到 cache 中
    
M- instantiateFactory
	-> ClassUtils.forName(instanceClassName, classLoader);
		?- 获得 Class 类
	->  if (!factoryClass.isAssignableFrom(instanceClass)) {
    	?- 判断是否实现了指定接口
    	-> throw new IllegalArgumentException
    -> 	ReflectionUtils.accessibleConstructor(instanceClass).newInstance();
    	-> 创建对象
```

