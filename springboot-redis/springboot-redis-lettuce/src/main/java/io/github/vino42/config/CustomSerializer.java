package io.github.vino42.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * =====================================================================================
 *
 * @Created :   2024/9/11 22:21
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class CustomSerializer<T> implements RedisSerializer<T> {


    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream outputStreamForDecompression = new ByteArrayOutputStream();

        try (ZstdInputStream zstdInputStream = new ZstdInputStream(inputStream)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = zstdInputStream.read(buffer)) != -1) {
                outputStreamForDecompression.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }

        String jsonData = new String(outputStreamForDecompression.toByteArray(), StandardCharsets.UTF_8);
        return JSON.parseObject(jsonData, new TypeReference<T>() {
        });

    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        byte[] result;
        String jsonString = JSON.toJSONString(t);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ZstdOutputStream zstdOutputStream = new ZstdOutputStream(outputStream)) {
            IOUtils.write(jsonString, zstdOutputStream, String.valueOf(StandardCharsets.UTF_8));
            result= outputStream.toByteArray();
        } catch (Exception e) {
            throw  new RuntimeException();
        }
        return result;



    }
}
