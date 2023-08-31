package io.github.vino42.serialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 21:56
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 
 * @Copyright : VINO
 * @Decription : gson序列化实现
 * =====================================================================================
 */
public class GsonSerializerImpl implements Serialize {
    private static Gson GSON = new GsonBuilder().create();

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return GSON.fromJson(new String(bytes, StandardCharsets.UTF_8), clazz);
    }

    @Override
    public <T> byte[] serialize(T object) {
        return GSON.toJson(object).getBytes(StandardCharsets.UTF_8);
    }
}
