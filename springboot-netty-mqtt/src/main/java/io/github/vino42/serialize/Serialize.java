package io.github.vino42.serialize;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 21:55
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : 自定义序列化接口
 * =====================================================================================
 */
public interface Serialize {
    // 反序列化方法
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // 序列化方法
    <T> byte[] serialize(T object);
}
