package io.github.vino42;

import io.github.vino42.log.AgentLog;
import javassist.*;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * =====================================================================================
 *
 * @Created :   2023/10/10 20:58
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class MyAgent {
    /**
     * 启动时加载
     */
    public static void premain(String  args, Instrumentation instrumentation) throws UnmodifiableClassException {
        System.err.println("agent startup , args is " + args);
        // 注册我们的文件下载函数

        instrumentation.addTransformer(new MyReqLoggerFileTransformer(),true);
        //重新装载代理类
        instrumentation.retransformClasses(MyReqLoggerFileTransformer.class);

    }


    /**
     * 运行时加载（attach api）
     */
    public static void agentmain(String args, Instrumentation inst) {
        System.err.println("agent running startup , args is " + args);
    }


    public static void main(String[] args) throws Exception {
        // javaassistant 获取类方法
//        ClassPool pool = ClassPool.getDefault();
//        CtClass cc = pool.get("io.github.vino42.log.AgentLog");
//        byte[] bytecode = cc.toBytecode();
//        System.out.println(Arrays.toString(bytecode));
//
//        System.out.println(cc.getName()); //获取类名
//        System.out.println(cc.getSimpleName()); //获取简要类名
//        System.out.println(cc.getSuperclass()); //获得父类
//        System.out.println(cc.getInterfaces()); //获得接口
//        ClassPool pool = ClassPool.getDefault();
//        CtClass cc = pool.get("io.github.vino42.log.AgentLog");
//        //当CtClass对象通过writeFile()、toClass()、toBytecode()转化为Class后，Javassist冻结了CtClass对象，
//        // 因此，JVM不允许再次加载Class文件，所以不允许对其修改。
//        //jas 生成方法
//        CtMethod m = new CtMethod(CtClass.intType,"add",
//                new CtClass[]{CtClass.intType,CtClass.intType},cc);
//        m.setModifiers(Modifier.PUBLIC);
//        m.setBody("{System.out.println(\"Hello!!!\");return $1+$2;}");
//
//        cc.addMethod(m);
//
//        //通过反射调用新生成的方法
//        Class clazz = cc.toClass();
//        Object obj = clazz.newInstance();  //通过调用Emp无参构造器，创建新的Emp对象
//        Method method = clazz.getDeclaredMethod("add", int.class,int.class);
//        Object result = method.invoke(obj, 200,300);
//        System.out.println(result);
        test03();

    }
    public static void test03() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("io.github.vino42.log.AgentLog");

        CtMethod cm = cc.getDeclaredMethod("sayHello",new CtClass[]{CtClass.intType});
        //方法执行前
        cm.insertBefore("System.out.println($1);System.out.println(\"start!!!\");");
        cm.insertAt(9, "int b=3;System.out.println(\"b=\"+b);");
        //方法执行后
        cm.insertAfter("System.out.println(\"after!!!\");");

        //通过反射调用新生成的方法
        Class clazz = cc.toClass();
        Object obj = clazz.newInstance();  //通过调用Emp无参构造器，创建新的Emp对象
        Method method = clazz.getDeclaredMethod("sayHello", int.class);
        method.invoke(obj, 300);
    }
}
