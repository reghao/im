package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author reghao
 * @date 2022-04-20 14:22:48
 */
@NoArgsConstructor
@Getter
public class ChatRecord extends BaseObject<Integer> {
    private long recordId;
    private long chatId;
    private int chatType;
    private long senderId;
    private long receiverId;
    private int msgType;
    private boolean isMark;
    private boolean isRead;
    private boolean isRevoke;
    private LocalDateTime createAt;

    public ChatRecord(long chatId, int chatType, long senderId, long receiverId, int msgType) {
        this.chatId = chatId;
        this.chatType = chatType;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msgType = msgType;
        this.isMark = false;
        this.isRead = false;
        this.isRevoke = false;
        this.createAt = LocalDateTime.now();
    }
}
