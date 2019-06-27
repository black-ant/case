## Java ������

#### ʲô���� ? 
1 .  Stream  ���Ǽ���Ԫ�� , �������ݽṹ , ����������
2 .  Stream  �����㷨�ͼ��� , �����ڸ߼� �� Iterator
3 .  ��ͬ�ڵ����� , Stream ���Բ��л����� , ���ݱ���Ϊ�ܶ�� , �ڲ�ͬ���߳��н��д���

#### ���Ĺ���
1 .  ��ȡ����Դ 
2 .  ����ת��
3 .  ִ�в�����ȡ��Ҫ�Ľ��

#### ������
###### ˫ð������

```
˫ð��������ǽ��������ɲ������ݸ���Ҫ�ķ��� ( Stream ) , ��Ϊ�������� , 
���� : : ������
```



###### ѡ����
```java
nums.stream() .filter(num -> num % 2 == 0)
// �˴���num ����������for ѭ���е�num 
for (final Integer num : nums) {
    if (num % 2 == 0) { }
}
```




#### ��ȡ���ķ�ʽ
###### Collection �� ����
1 .  Collection.stream () ---  list.stream();
2 .  Collection.parallelStream ()
3 .  Arrays.stream( T array ) --- Arrays.stream(strArray);
4 .  Stream.of ( )  --- Stream.of(strArray);
5 .  java.io.BufferedReader.lines()

###### ��̬����
1 .  Java.util.stream.IntStream.range ( )
2 .  Java.nio.file.Files.walk ( )

###### �Լ�����
1 .   java.util.Spliterator
2 .  Random.ints()
3 .  BitSet.stream()
4 .  Pattern.splitAsStream(java.lang.CharSequence)
5 .  JarFile.stream()

#### ���Ĳ�������
1  .  Intermediate : һ��������Ը���������� intermediate ���� . 

��ʱ�����Ǵ��� , �������й��˺�ӳ�� , Ȼ�󷵻����� 
��ʱ�Ĳ�����Ϊ���� , �����ǵ��� , ��û�п�ʼ����

2  .  Terminal : һ����ֻ����һ��Terminal  ���� , ����ִ�к� , ������ʹ���� ,������������ . �˺� , ��ʼ���ı��� , 


#### ����ʹ��
IntStream.range(1, 3).forEach(System.out::println);


#### ����ת��
###### Array
 stream.toArray(String[]::new);

###### collection



```java
// toList : ���������е�Ԫ���ռ���List ��
stream.collect(Collectors.toList());

// toCollection : �����е�Ԫ���ռ��������Ĺ�ӦԴ�����ļ�����
 stream.collect(Collectors.toCollection(ArrayList::new));

// toSet : ���������е�Ԫ�ر��浽Set������,ɾ���ظ���
stream.collect(Collectors.toSet());

stream.collect(Collectors.toCollection(Stack::new));

// stream ����ת��
String[] testStrings = { "java", "react", "angular", "vue" };
List<String> list = Stream.of(testStrings).collect(Collectors.toList());

```



###### String 
stream.collect(Collectors.joining()).toString();
list.stream().collect(Collectors.joining(" | ")) === ���ӷ��м䴩��
list.stream().collect(Collectors.joining(" || ", "Start--", "--End")) === ���ӷ��м估ǰ��

#### ������
��ȡ�������Կ���һ����ʽ�߼������� , ͨ�� filter ʱ ����ı�����Ϊ����ʱ�Ķ��� , 


#### ���Ĳ���

###### Intermediate ����

```java
> map (mapToInt, ��) 
> ------ > List<String> output = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());

> ------ ? �����еĵ���ת����Сд
> ------ > nums . stream() . map( n -> n * n ) . collect ( Collectors.toList() );
> ------ ? ��ƽ����

> flatMap : ������㼶��ƽ�� �� �����stream �ں�Ϊһ��stream
> ------ > inputStream.flatMap ( (childList ) -> childList.stream () );
> ------ ? ��ƽ����

> filter : ���� ,ͨ�����˵�Ԫ�ر������������µ� stream
> ------ > Stream<T> filter(Predicate<? super T> predicate) === Predicate ��һ������ʽ�ӿ�
> ------ > Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);

// ��ƽ�������
 reader.lines().flatMap( line -> Stream.of ( line.split(REGEXP) ) ).filter(word -> word.length() > 0).collect(Collectors.toList());
// �����ַ�������
list.stream().filter(p -> p.startsWith("j")).count();

> distinct : distinct����Object.equals(Object)ʵ�� ,����ȥ��
> ------- > nums.stream() .filter(num -> num % 2 == 0) . distinct() . collect(Collectors.toList());

> sorted : ��Stream ���� sorted �������� , ����ͨ�� map��filter��limit��skip����ѡ����������
> ------- > persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
```

peek
```
limit : limit ���� Stream ��ǰ�� n ��Ԫ��
------- > nums. stream() .filter(n-> n>2 ).limit(2) . collect( Collectors . toList () );

skip :  �ӵ�ǰ n ��Ԫ��
------- > nums. stream() .filter(n-> n>2 ).skip (2) . collect( Collectors . toList () );
parallel
sequential
unordered
```



###### Terminal ����



```
forEach : 
------- > roster.stream().filter ( p -> p.getGender() == Person.Sex.MALE) . forEach ( p -> System.out.println ( p.getName() ) );
```

forEachOrdered

toArray

```java
reduce : �� Stream Ԫ���������
-------- >  Stream.of("A", "B", "C", "D").reduce("", String::concat);
? -------- reduce ����Ĳ��� : ��һ����ʾ��ʼ��λ�� , �����Ǵ���ķ���
collect
// min : ���ַ������������ֵ
Stream.of(testStrings).max((p1, p2) -> Integer.compare(p1.length(), p2.length()));
// max : �����󳤶�
br.lines().mapToInt(String::length).max().getAsInt();
```

count 
anyMatch
allMatch
noneMatch
findFirst : findFirst���ڷ������������ĵ�һ��Ԫ��
-------- > students.stream().filter(student ->student .getage()>20 ).findFirst();

findAny : findAny�����findFirst���������ڣ�findAny��һ�����ص�һ�������Ƿ�������һ��

iterator

###### Short-circuiting 
> anyMatch : Stream ��ֻҪ��һ��Ԫ�ط��ϴ���� predicate������ true
> ? ------- persons.stream(). anyMatch(p -> p.getAge() < 12);

> allMatch : Stream ��ȫ��Ԫ�ط��ϴ���� predicate������ true
> ? ------- persons.stream(). allMatch(p -> p.getAge() > 18);

> noneMatch :  Stream ��û��һ��Ԫ�ط��ϴ���� predicate������ true 


> findFirst : ���� Stream �ĵ�һ��Ԫ��
> findAnt


###### ��Լ����
```java
reduce : �Բ�����������ļ��Ͻ��н�һ������
students.stream() .filter(student -> "�������ѧ".equals(student.getMajor())) .map(Student::getAge) .reduce(0, (a, b) -> a + b);

students.stream() .filter(student -> "�������ѧ".equals(student.getMajor())) .map(Student::getAge) .reduce(0, Integer::sum);

students.stream() .filter(student -> "�������ѧ".equals(student.getMajor())) .map(Student::getAge) .reduce(Integer::sum); 
```



```java
> GROUP BY : �ùؼ������ڷ������

// ��������
> students.stream().collect(Collectors.groupingBy(Student::getSchool))

// �༶����
> students.stream().collect(
>         Collectors.groupingBy(Student::getSchool,  // һ�����飬��ѧУ
>         Collectors.groupingBy(Student::getMajor)));  // �������飬��רҵ

// partitioningBy : ���� ,������groupBy ,������ֻ��true , false .
> students.stream().collect(Collectors.partitioningBy(student -> "�人��ѧ".equals(student.getSchool())));
```


##### ���� һ : �� List AList �� ÿ�� Ԫ�ؽ��в���ת�������� ��������� C ���� List<C> BList 

```java
List<JSONObject> itemjson = new LinkedList<JSONObject>();
List<A> aList = ... 
itemCW.stream().map(c -> {
            JSONObject nodeitem = new JSONObject();
            nodeitem.put("whtype", 0);
            return nodeitem;
}).forEach(c -> itemjson.add(c));
```

##### ������ �� �� Map ����ѭ������

```java
       realmTO.getTemplates().forEach((key, template) -> {
            AnyType type = anyTypeDAO.find(key);
            anyTemplate.set(template);
        });
// for-each  ��ʱ�� �� ���Դ��� key �� ���� ����������ʹ��
```



##### ������ �� 



���������ܷ��� :
1 .  ����
2 . ȥ��
3 . ɸѡ
4 . ��ȡ������Ϣ
5 . �ж��Ƿ����
6 . ������
7 . ���
8 . ����
9 . ��ʽ



https://www.cnblogs.com/shenlanzhizun/p/6027042.html
https://segmentfault.com/a/1190000004171551?utm_source=tag-newest
https://www.cnblogs.com/Dorae/p/7779246.html
https://www.cnblogs.com/cjsblog/p/8992048.html
https://www.jianshu.com/p/9fe8632d0bc2
������Դ����
http://www.importnew.com/26090.html