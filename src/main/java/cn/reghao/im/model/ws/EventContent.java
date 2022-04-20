package cn.reghao.im.model.ws;

/**
 * @author reghao
 * @date 2022-04-20 18:59:30
 */
public class EventContent<T> {
    private int talkType;
    private long senderId;
    private long receiverId;
    private T data;

    public EventContent(int talkType, long senderId, long receiverId, T data) {
        this.talkType = talkType;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.data = data;
    }
}
