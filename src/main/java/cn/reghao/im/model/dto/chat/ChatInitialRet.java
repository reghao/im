package cn.reghao.im.model.dto.chat;

import cn.reghao.im.model.po.chat.ChatDialog;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 20:48:06
 */
@Data
public class ChatInitialRet {
    private long id;
    private int talkType;
    private boolean isDisturb;
    private boolean isTop;
    private boolean isRobot;
    private int unreadNum;

    // 根据 MsgType 和 FileMsgType 来确定值
    private String msgText;
    private String updatedAt;

    private long receiverId;
    private String name;
    private String avatar;
    private boolean isOnline;
    private String remarkName;

    public ChatInitialRet(ChatDialog chatDialog, ChatUserInfo chatUserInfo) {
        this.id = chatDialog.getId();
        this.talkType = chatDialog.getChatType();
        this.isDisturb = chatDialog.isDisturb();
        this.isTop = chatDialog.isTop();
        this.isRobot = chatDialog.isRobot();
        this.unreadNum = chatDialog.getUnreadNum();

        this.msgText = "";
        this.updatedAt = DateTimeConverter.format(System.currentTimeMillis());

        this.receiverId = chatUserInfo.getReceiverId();
        this.name = chatUserInfo.getName();
        this.avatar = chatUserInfo.getAvatar();
        this.isOnline = chatUserInfo.isOnline();
        this.remarkName = chatUserInfo.getRemarkName();
    }
}
