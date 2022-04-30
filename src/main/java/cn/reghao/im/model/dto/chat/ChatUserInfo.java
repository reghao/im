package cn.reghao.im.model.dto.chat;

import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-30 10:38:24
 */
@Getter
public class ChatUserInfo {
    private long receiverId;
    private String name;
    private String avatar;
    private boolean isOnline;
    private String remarkName;
}
