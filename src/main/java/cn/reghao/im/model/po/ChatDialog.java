package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author reghao
 * @date 2022-04-16 20:48:06
 */
@NoArgsConstructor
@Getter
@Setter
public class ChatDialog extends BaseObject<Integer> {
    private long chatId;
    private int chatType;
    private long receiverId;
    private boolean isDisturb;
    private boolean isOnline;
    private boolean isRobot;
    private boolean isTop;
    private long userId;
    private int unreadNum;
    private long lastSendUserId;
    private LocalDateTime updateAt;
    private String msgText;

    public ChatDialog(long chatId, int chatType, long receiverId, long userId) {
        this.chatId = chatId;
        this.chatType = chatType;
        this.receiverId = receiverId;
        this.isDisturb = false;
        this.isOnline = true;
        this.isRobot = false;
        this.isTop = false;
        this.userId = userId;
        this.unreadNum = 0;
        this.lastSendUserId = userId;
        this.updateAt = LocalDateTime.now();
        this.msgText = "";
    }
}
