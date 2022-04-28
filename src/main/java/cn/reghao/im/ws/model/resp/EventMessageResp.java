package cn.reghao.im.ws.model.resp;

import cn.reghao.im.ws.model.EventType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-21 11:05:56
 */
@Getter
@Setter
public class EventMessageResp <T> {
    private final String event;
    private final T content;

    public EventMessageResp(EventType eventType, T content) {
        this.event = eventType.name();
        this.content = content;
    }
}
