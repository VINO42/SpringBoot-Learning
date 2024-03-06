### java agent 技术

* 应用场景
  * 性能分析：通过Java Agent可以在程序运行时对性能进行监控和分析，以便进行优化和调试。

  * AOP编程：通过Java Agent可以在程序运行时动态地修改类的字节码，以实现AOP编程。

  * 安全监控：Java Agent可以用于安全监控，例如监控应用程序的网络流量、文件读写等操作。

  * 原理

      了解原理之前需要先了解 Instrumentation 这个接口,这个接口主要是帮助开发人员对字节码的增强

      ```
      public interface Instrumentation {
      /**
       * 注册Class文件转换器，转换器用于改变Class文件二进制流的数据
       *
       * @param transformer          注册的转换器
       * @param canRetransform       设置是否允许重新转换
       */
      void addTransformer(ClassFileTransformer transformer, boolean canRetransform);

      /**
       * 移除一个转换器
       *
       * @param transformer          需要移除的转换器
       */
      boolean removeTransformer(ClassFileTransformer transformer);
  
      /**
       * 在类加载之后，重新转换类，如果重新转换的方法有活跃的栈帧，那些活跃的栈帧继续运行未转换前的方法
       *
       * @param 重新转换的类数组
       */
      void retransformClasses(Class<?>... classes) throws UnmodifiableClassException;
  
      /**
       * 当前JVM配置是否支持重新转换
       */
      boolean isRetransformClassesSupported();

      /**
       * 获取所有已加载的类
       */
      @SuppressWarnings("rawtypes")
      Class[] getAllLoadedClasses();
      }
      public interface ClassFileTransformer {
      // className参数表示当前加载类的类名，classfileBuffer参数是待加载类文件的字节数组
      // 调用addTransformer注册ClassFileTransformer以后，后续所有JVM加载类都会被它的transform方法拦截
      // 这个方法接收原类文件的字节数组，在这个方法中做类文件改写，最后返回转换过的字节数组，由JVM加载这个修改过的类文件
      // 如果transform方法返回null，表示不对此类做处理，如果返回值不为null，JVM会用返回的字节数组替换原来类的字节数组
      byte[] transform(  ClassLoader         loader,
      String              className,
      Class<?>            classBeingRedefined,
      ProtectionDomain    protectionDomain,
      byte[]              classfileBuffer)
      throws IllegalClassFormatException;
      }
      ```
    
 * 使用方式
   * 在JVM启动的时候添加一个Agent jar包
   * JVM运行以后在任意时刻通过Attach API远程加载Agent的jar包
   * Agent

* 创建
  
  * 创建agent项目
  * 编写代理类 实现java.lang.instrument.ClassFileTransformer 接口
  * 定义代理配置文件 创建一个MANIFEST.MF文件 指定代理类并打包为jar文件
    * 手动配置，手动配置只需要在resources文件下创建META-INF/MENIFEST.MF文件即可
    * 除去手动配置外，可以使用maven插件在打包阶段自动生成，maven的插件配置如下：
  * 加载代理 在JVM启动参数中指定代理jar文件
  * 修改字节码文件 在代理类的transform文件中 对需要进行修改的类进行字节码转换
  * 重新定义类 使用ClassDefinition 类重新定义被修改的类
  * 运行应用程序 测试字节码修改效果