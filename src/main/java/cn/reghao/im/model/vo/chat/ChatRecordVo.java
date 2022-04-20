package cn.reghao.im.model.vo.chat;

import cn.reghao.im.model.po.ChatRecord;
import cn.reghao.im.model.vo.message.FileMsgResult;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.im.model.po.TextMessage;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-17 16:18:18
 */
@NoArgsConstructor
@Getter
@Setter
public class ChatRecordVo {
    private int id;
    private int talkType;
    private long userId;
    private long receiverId;
    private int msgType;

    // TODO 根据 MsgType 来确定
    private String content;
    private FileMsgResult file;

    private boolean isMark;
    private boolean isRead;
    private boolean isRevoke;
    private String nickname;
    private String avatar;
    private String createdAt;

    public ChatRecordVo(ChatRecord chatRecord, String nickname, String avatar, int chatType, long receiverId) {
        this.id = chatRecord.getId();
        this.talkType = chatType;
        this.userId = chatRecord.getSenderId();
        this.receiverId = receiverId;
        this.msgType = chatRecord.getMsgType();
        this.isMark = true;
        this.isRead = true;
        this.isRevoke = false;
        this.nickname = nickname;
        this.avatar = avatar;
        this.createdAt = DateTimeConverter.format(chatRecord.getCreateAt());
    }
}
