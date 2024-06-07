package io.github.vino42.configuaration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/3 23:55
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration
public class CustomMybatisPlusConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMybatisPlusConfiguration.class);

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            // 创建并添加拦截器实例到配置中
            configuration.addInterceptor(new MybatisLikeSqlEscapeInterceptor());
        };
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        /**
         * 分页插件配置
         */
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setMaxLimit(200L);
        /**
         * 非法sql插件
         */
//        IllegalSQLInnerInterceptor illegalSQLInnerInterceptor = new IllegalSQLInnerInterceptor();
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //添加分页插件
        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor);
        // 防止 修改与删除时对全表进行操作
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //非法sql校验
//        mybatisPlusInterceptor.addInnerInterceptor(illegalSQLInnerInterceptor);
        LOGGER.trace("Mybatis Plus Interceptor Auto Configure.");
        return mybatisPlusInterceptor;
    }

}
