package cn.reghao.im.model.vo.message;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:49:38
 */
@Data
public class Record {
    private String avatar;
    private String content;
    private String createAt;
    private long id;
    private boolean isMark;
    private boolean isRead;
    private boolean isRevoke;
    private int msgType;
    private String nickname;
    private long receiverId;
    private int talkType;
    private long userId;
}
