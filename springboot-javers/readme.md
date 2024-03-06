### springboot-javers
 * javers 是一个数据审计框架 springboot-javers 提供版本控制 事务管理 合并冲突 数据库迁移等
 * 最常见的应用场景是 对比两个对象的diff 属性值的diff
 * 对比算法

   ```
   Javers javers = JaversBuilder.javers()
             .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
             .build();
   ```
   * Levenshtein: 是最智能的比较算法，一般推荐使用。但是需要注意的是如果比较元素超过300个以上效率就会变得很慢
   * Simple：主要优点是速度快，计算复杂度线性，主要缺点是输出过于冗长
   * Set: 如果您不关心集合的顺序，请选择 Set 算法。在进行比较之前，JaVers 会将所有 list 转换为 set。此算法产生最简洁的输出(只有 ValueAdded 和 ValueRemoved)
 * javers-spring 支持spring-data 相关集成
 * javers.compare对象比较
 * javers.compareCollections 集合比较
 * implements CustomValueComparator<BigDecimal> 实现自定义比较器
 * 注解
    ``` 
    类级别:
   
    @Entity
    将给定的类(及其所有子类)声明为 Entity 类型
    
    @ValueObject
    将给定的类(及其所有子类)声明为 Value Object 类型。
    
    @Value
    @DiffIgnore
    @ShallowReference
    @IgnoreDeclaredProperties
    @TypeName
    
   属性:
   
    @Id
    声明一个实体的 id 属性,没有id无法比对出是否为新增对象

    @ShallowReference
    声明一个属性为浅引用。只能用于实体类型属性。目标 Entity 实例的所有属性(Id 除外)都被忽略。
    @PropertyName 
   
    在比较两个类时，可能需要只比较部分字段或不比较部分字段，可以考虑使用以下两个注解
    //在一个类中，在字段上添加该注解，该类中只比较有注解的字段
    @DiffInclude
    //在一个类中，在字段上添加该注解，该类中忽略比较有注解的字段
    @DiffIgnore
   
    ```