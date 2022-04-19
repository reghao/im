package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author reghao
 * @date 2022-04-16 20:48:06
 */
@NoArgsConstructor
@Data
public class ChatDialog extends BaseObject<Integer> {
    private int chatType;
    private boolean isDisturb;
    private boolean isOnline;
    private boolean isRobot;
    private boolean isTop;
    private long senderId;
    private long receiverId;
    private int unreadNum;
    private LocalDateTime updateAt;
    private String msgText;

    public ChatDialog(int chatType, long senderId, long receiverId) {
        this.chatType = chatType;
        this.isDisturb = false;
        this.isOnline = true;
        this.isRobot = false;
        this.isTop = false;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.unreadNum = 0;
        this.updateAt = LocalDateTime.now();
        this.msgText = "";
    }
}
