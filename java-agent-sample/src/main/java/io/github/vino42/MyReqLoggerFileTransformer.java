package io.github.vino42;


import io.github.vino42.log.AgentLog;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/10/10 20:08
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class MyReqLoggerFileTransformer implements ClassFileTransformer {
    private static final List<String> SYSTEM_CLASS_PREFIX = Arrays.asList("java", "sum", "jdk");

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        if (!isSystemClass(className)) {
//            System.out.println("load class " + className);
//        }

        if (className.contains("Controller")) {
            CtClass logClass = null;
            try {
                System.out.println("======================================locakback日志增强 className:"+className);

                // 从ClassPool获得CtClass对象
                ClassPool pool = ClassPool.getDefault();
                pool.importPackage("io.github.vino42.log");
                System.out.println("======================================locakback日志增强成功");
                AgentLog.enchance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return classfileBuffer;
    }

    /**
     * 判断一个类是否为系统类
     *
     * @param className 类名
     * @return System Class then return true,else return false
     */
    private boolean isSystemClass(String className) {
        // 假设系统类的类名不为NULL而且不为空
        if (null == className || className.isEmpty()) {
            return false;
        }

        for (String prefix : SYSTEM_CLASS_PREFIX) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
