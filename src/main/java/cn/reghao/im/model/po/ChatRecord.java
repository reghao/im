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
    private long senderId;
    private int msgType;
    private boolean isMark;
    private boolean isRead;
    private boolean isRevoke;
    private LocalDateTime createAt;

    public ChatRecord(long chatId, long senderId, int msgType) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.msgType = msgType;
        this.isMark = false;
        this.isRead = false;
        this.isRevoke = false;
        this.createAt = LocalDateTime.now();
    }
}
