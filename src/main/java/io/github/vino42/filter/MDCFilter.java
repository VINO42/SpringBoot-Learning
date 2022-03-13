package io.github.vino42.filter;

import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 21:01
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String requestId = UUID.randomUUID().toString();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String header = response.getHeader("X-req-id");
            if (StrUtil.isBlank(header)) {
                MDC.put("requestId", requestId);
                response.addHeader("X-req-id", requestId);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove("requestId");
        }
    }
}
