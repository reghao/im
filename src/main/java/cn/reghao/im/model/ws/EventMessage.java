package cn.reghao.im.model.ws;

import cn.reghao.im.model.constant.EventType;
import cn.reghao.im.model.ws.EventContent;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-19 12:20:56
 */
@Getter
public class EventMessage<T> {
    private final String event;
    private final EventContent<T> content;

    public EventMessage(EventType eventType, EventContent<T> content) {
        this.event = eventType.name();
        this.content = content;
    }
}
