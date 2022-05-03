package cn.reghao.im.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author reghao
 * @date 2021-06-11 13:31:20
 */
public class Appenders {
    private static final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    public static Appender<ILoggingEvent> esAppender(String appId, ElasticsearchRestTemplate restTemplate) {
        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.start();

        EsAppender esAppender = new EsAppender(restTemplate);
        esAppender.setContext(loggerContext);
        esAppender.start();
        return esAppender;
    }

    public static Appender<ILoggingEvent> fileAppender() {
        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.start();

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        fileAppender.setFile("file");
        fileAppender.setEncoder(layoutEncoder);
        fileAppender.setContext(loggerContext);
        fileAppender.start();
        return fileAppender;
    }

    public static Appender<ILoggingEvent> consoleAppender(LoggerContext loggerContext) {
        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %c %M %L - %msg%n");
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.start();

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.start();
        return consoleAppender;
    }
}
