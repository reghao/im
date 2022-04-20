package cn.reghao.im.model.ws;

import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.im.model.po.TextMessage;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-19 15:27:06
 */
@Data
public class TalkEvtMsg {
    private int talkType;
    private long userId;
    private long receiverId;
    private int msgType;
    private String content;
    private String file;
    private String createdAt;

    private String nickname;
    private String avatar;
    private String groupName;
    private String groupAvatar;

    public TalkEvtMsg(TextMessage textMessage, String nickname, String avatar, int talkType, long receiverId) {
        this.talkType = talkType;
        this.userId = textMessage.getSenderId();
        this.receiverId = receiverId;
        this.msgType = 1;
        this.content = textMessage.getText();
        this.createdAt = DateTimeConverter.format(textMessage.getCreateAt());
        this.nickname = nickname;
        this.avatar = avatar;
    }
}
