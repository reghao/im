package cn.reghao.im.model.ws.resp;

import cn.reghao.im.model.constant.EventType;
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
