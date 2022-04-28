package cn.reghao.im.model.po.chat;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-20 10:43:06
 */
@NoArgsConstructor
@Data
public class Chat extends BaseObject<Integer> {
    private long chatId;
    private int chatType;

    public Chat(int chatType) {
        this.chatType = chatType;
    }
}
