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
    private int unreadNum;

    private long receiverId;
    private String name;
    private String remarkName;
    private String avatar;
    private String updatedAt;
    private String msgText;

    public ChatDialogVo(ChatDialog chatDialog, String name, String remarkName, String avatar) {
        this.id = chatDialog.getId();
        this.isDisturb = chatDialog.isDisturb();
        this.isOnline = chatDialog.isOnline();
        this.isRobot = chatDialog.isRobot();
        this.isTop = chatDialog.isTop();
        this.talkType = 1;
        this.unreadNum = chatDialog.getUnreadNum();

        this.receiverId = chatDialog.getReceiverId();
        this.name = name;
        this.remarkName = remarkName;
        this.avatar = avatar;
        this.updatedAt = DateTimeConverter.format(chatDialog.getUpdateAt());
        this.msgText = chatDialog.getMsgText();
    }
}
