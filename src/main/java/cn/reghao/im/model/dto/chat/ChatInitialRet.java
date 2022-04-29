package cn.reghao.im.model.dto.chat;

import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.im.model.po.chat.ChatDialog;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 20:48:06
 */
@Data
public class ChatInitialRet {
    private long id;
    private boolean isDisturb;
    private boolean isOnline;
    private boolean isRobot;
    private boolean isTop;
    private int talkType;
    private int unreadNum;

    private long receiverId;
    private String name;
    private String remarkName;
    private String avatar;
    private String updatedAt;
    private String msgText;

    public ChatInitialRet(ChatDialog chatDialog, String name, String remarkName, String avatar) {
        this.id = chatDialog.getId();
        this.isDisturb = chatDialog.isDisturb();
        this.isOnline = true;
        this.isRobot = chatDialog.isRobot();
        this.isTop = chatDialog.isTop();
        this.talkType = chatDialog.getChatType();
        this.unreadNum = chatDialog.getUnreadNum();

        this.receiverId = chatDialog.getReceiverId();
        this.name = name;
        this.remarkName = remarkName;
        this.avatar = avatar;
        this.updatedAt = DateTimeConverter.format(System.currentTimeMillis());
        this.msgText = "test";
    }
}