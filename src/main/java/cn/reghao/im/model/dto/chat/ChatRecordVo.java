package cn.reghao.im.model.dto.chat;

import cn.reghao.im.model.po.chat.ChatRecord;
import cn.reghao.im.model.dto.message.CodeBlockResult;
import cn.reghao.im.model.dto.message.FileMsgResult;
import cn.reghao.im.model.dto.user.UserInfo;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
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
    private long receiverId;
    private boolean isMark;
    private boolean isRead;
    private boolean isRevoke;
    private String createdAt;
    private int msgType;
    // TODO 根据 MsgType 来确定
    private String content;
    private CodeBlockResult codeBlock;
    private FileMsgResult file;

    private long userId;
    private String nickname;
    private String avatar;

    public ChatRecordVo(ChatRecord chatRecord, UserInfo userInfo) {
        this.id = chatRecord.getId();
        this.talkType = chatRecord.getChatType();
        this.userId = userInfo.getUid();
        this.receiverId = chatRecord.getReceiverId();
        this.msgType = chatRecord.getMsgType();
        this.isMark = chatRecord.isMark();
        this.isRead = chatRecord.isRead();
        this.isRevoke = chatRecord.isRevoke();
        this.nickname = userInfo.getNickname();
        this.avatar = userInfo.getAvatar();
        this.createdAt = DateTimeConverter.format(chatRecord.getCreateAt());
    }
}
