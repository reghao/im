package cn.reghao.im.ws.model.resp;

import lombok.AllArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-21 11:32:33
 */
@AllArgsConstructor
public class EvtTalkRevokeResp {
    private int talkType;
    private long senderId;
    private long receiverId;
    private long recordId;
}
