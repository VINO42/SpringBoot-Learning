package io.github.vino42.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

/**
 * =====================================================================================
 *
 * @Created :   2023/10/13 18:56
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */

@Endpoint(id = "custom")
@Component
public class CustomEndpoint {

    @ReadOperation
    @WriteOperation
    public String read() {


        return  "readness  writness ok";
    }


}
