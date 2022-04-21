package cn.reghao.im.model.ws.resp;

/**
 * @author reghao
 * @date 2022-04-21 11:32:33
 */
public class EvtTalkResp<U> {
    private int talkType;
    private long senderId;
    private long receiverId;
    private U data;

    public EvtTalkResp(int talkType, long senderId, long receiverId, U data) {
        this.talkType = talkType;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.data = data;
    }
}
