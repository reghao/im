package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author reghao
 * @date 2022-04-18 17:58:48
 */
@NoArgsConstructor
@Getter
public class TextMessage extends BaseObject<Integer> {
    private int chatType;
    private long senderId;
    private long receiverId;
    private String text;
    private LocalDateTime createAt;

    public TextMessage(int chatType, long senderId, long receiverId, String text) {
        this.chatType = chatType;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.createAt = LocalDateTime.now();
    }
}
