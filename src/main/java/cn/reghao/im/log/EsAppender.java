package cn.reghao.im.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import cn.reghao.jutil.jdk.serializer.JsonConverter;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * 日志写到 ElasticSearch
 *
 * @author reghao
 * @date 2022-05-04 00:37:21
 */
public class EsAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private final String appId;
    private final ElasticsearchRestTemplate restTemplate;

    public EsAppender(ElasticsearchRestTemplate restTemplate) {
        this.appId = "appId";
        this.restTemplate = restTemplate;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent event) {
        AppLog nodeLogDTO = nodeLogDTO(event);
        String jsonPayload = JsonConverter.objectToJson(nodeLogDTO);
    }

    private AppLog nodeLogDTO(ILoggingEvent event) {
        AppLog nodeLogDTO = new AppLog();
        nodeLogDTO.setMachineId("Machine.ID");
        nodeLogDTO.setAppId(appId);
        nodeLogDTO.setTimestamp(event.getTimeStamp());
        nodeLogDTO.setLevel(event.getLevel().toString());
        nodeLogDTO.setThreadName(event.getThreadName());
        nodeLogDTO.setLoggerName(event.getLoggerName());
        nodeLogDTO.setMessage(event.getFormattedMessage());
        return nodeLogDTO;
    }
}
