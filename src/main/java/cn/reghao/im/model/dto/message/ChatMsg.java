package cn.reghao.im.model.dto.message;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-17 16:57:42
 */
@Data
public class ChatMsg {
    protected long receiverId;
    protected int talkType;
}
