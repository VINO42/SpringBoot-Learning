package io.github.vino42.configuration;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.util.Date;

/**
 * 用于p6spy的自定义配置
 */
public class P6SpyLoggerStrategy implements MessageFormattingStrategy {


    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        System.out.println();
        if (!"".equals(sql.trim())) {
            StringBuffer sb = new StringBuffer();
            sb.append("\r\n" + "============== SQL LOGGER BEGIN ==============");
            sb.append("\r\n");
            String sqlExecuteTime = "SQL 执行时间       :" + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_PATTERN) + "\n";
            sb.append(sqlExecuteTime);
            String elapsedStr = "SQL 执行毫秒       :" + elapsed + "ms" + "\n";
            sb.append(elapsedStr);
            String sqlPrint = "SQL 执行语句       :" + sql;
            sb.append(sqlPrint);
            sb.append("\r\n");
            //String sqlPrint = !"".equals(sql.trim()) ? this.format.format(new Date()) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + sql + ";" : "";
            String sqlEnd = "==============  SQL LOGGER END  ==============" + "\n";
            sb.append(sqlEnd);

            return sb.toString();
        }
        return "";
    }
}

