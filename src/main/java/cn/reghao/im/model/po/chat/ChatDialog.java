package cn.reghao.im.model.po.chat;

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
    private boolean disturb;
    private boolean robot;
    private boolean top;
    private boolean display;
    private long userId;
    private int unreadNum;

    public ChatDialog(long chatId, int chatType, long receiverId, long userId) {
        this.chatId = chatId;
        this.chatType = chatType;
        this.receiverId = receiverId;
        this.disturb = false;
        this.robot = false;
        this.top = false;
        this.display = true;
        this.userId = userId;
        this.unreadNum = 0;
    }
}
