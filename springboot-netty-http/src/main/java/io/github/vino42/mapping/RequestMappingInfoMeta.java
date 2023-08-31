package io.github.vino42.mapping;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 23:10
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class RequestMappingInfoMeta {
    private String className;
    private String url;
    private Set<String> httpMethods;
    private Method method;
    private Class paramaClaz;

}
