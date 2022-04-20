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
    private long chatId;
    private long senderId;
    private String text;
    private LocalDateTime createAt;

    public TextMessage(long chatId, long senderId, String text) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.text = text;
        this.createAt = LocalDateTime.now();
    }
}
