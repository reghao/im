package cn.reghao.im.ws.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-19 15:08:35
 */
@Getter
@Setter
public class Talk {
    private long senderId;
    private long receiverId;
    private int talkType;
    private Object data;

    public Talk(long senderId, long receiverId, int talkType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.talkType = talkType;
    }
}
