package cn.reghao.im.model.vo.chat;

import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.im.model.po.TextMessage;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-17 16:18:18
 */
@Data
public class ChatRecord {
    private int id;
    private int talkType;
    private long userId;
    private long receiverId;
    private int msgType;
    // TODO 根据 MsgType 来确定
    private String content;
    private boolean isMark;
    private boolean isRead;
    private boolean isRevoke;
    private String nickname;
    private String avatar;
    private String createdAt;

    public ChatRecord(TextMessage textMessage, String nickname, String avatar, int talkType, long receiverId) {
        this.id = textMessage.getId();
        this.talkType = talkType;
        this.userId = textMessage.getSenderId();
        this.receiverId = receiverId;
        this.msgType = 1;
        this.content = textMessage.getText();
        this.isMark = true;
        this.isRead = true;
        this.isRevoke = false;
        this.nickname = nickname;
        this.avatar = avatar;
        this.createdAt = DateTimeConverter.format(textMessage.getCreateAt());
    }
}
