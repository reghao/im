package cn.reghao.im.model.vo.chat;

import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.im.model.po.ChatDialog;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 20:48:06
 */
@Data
public class ChatDialogVo {
    private long id;
    private boolean isDisturb;
    private boolean isOnline;
    private boolean isRobot;
    private boolean isTop;
    private int talkType;
    private long receiverId;
    private String name;
    private String remarkName;
    private String avatar;

    private int unreadNum;
    private String updatedAt;
    private String msgText;

    public ChatDialogVo(ChatDialog chatDialog, String receiverName, String receiverRemarkName, String receiverAvatar) {
        this.id = chatDialog.getId();
        this.isDisturb = chatDialog.isDisturb();
        this.isOnline = chatDialog.isOnline();
        this.isRobot = chatDialog.isRobot();
        this.isTop = chatDialog.isTop();
        this.talkType = chatDialog.getChatType();
        this.receiverId = chatDialog.getReceiverId();
        this.unreadNum = chatDialog.getUnreadNum();
        this.updatedAt = DateTimeConverter.format(chatDialog.getUpdateAt());
        this.msgText = chatDialog.getMsgText();
        this.name = receiverName;
        this.remarkName = receiverRemarkName;
        this.avatar = receiverAvatar;
    }
}
