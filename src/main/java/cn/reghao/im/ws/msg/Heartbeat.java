package cn.reghao.im.ws.msg;

import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-19 12:17:37
 */
@Getter
public class Heartbeat {
    private final long timestamp;

    public Heartbeat() {
        this.timestamp = System.currentTimeMillis();
    }
}
