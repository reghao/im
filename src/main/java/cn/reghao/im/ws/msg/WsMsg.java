package cn.reghao.im.ws.msg;

import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-19 12:20:56
 */
@Getter
public class WsMsg<T> {
    private final String event;
    private final T content;

    public WsMsg(ImEvent imEvent, T content) {
        this.event = imEvent.name();
        this.content = content;
    }
}
