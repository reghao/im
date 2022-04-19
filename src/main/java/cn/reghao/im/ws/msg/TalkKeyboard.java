package cn.reghao.im.ws.msg;

/**
 * @author reghao
 * @date 2022-04-19 13:51:33
 */
public class TalkKeyboard {
    private long senderId;
    private long receiverId;

    public TalkKeyboard(long senderId, long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
