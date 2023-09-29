package io.github.vino42.configuration;

import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.JdbcEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/4 0:50
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
public class CustomMybatisFlexConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(FlexConfiguration flexConfiguration) {
        /**
         * 开启日志打印
         */
//        flexConfiguration.setLogImpl(StdOutImpl.class);
    }
}
