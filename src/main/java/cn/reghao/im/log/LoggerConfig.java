package cn.reghao.im.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 编程方式配置 logback，相当于 logback-spring-bak.xml 配置
 *
 * @author reghao
 * @date 2021-02-23 23:53:35
 */
public class LoggerConfig {
    private static LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    public static void initLogger(List<Appender<ILoggingEvent>> appenders) {
        String env = System.getProperty("spring.profiles.active");
        if (!"dev".equals(env)) {
            // TODO 非 dev 环境则禁止日志写入 console
        }

        Logger rootLogger = loggerContext.getLogger("ROOT");
        rootLogger.setAdditive(false);
        rootLogger.setLevel(Level.INFO);
        appenders.forEach(rootLogger::addAppender);
    }
}
