package cn.reghao.im.model.dto.group;

import cn.reghao.im.model.po.contact.ChatGroup;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reghao
 * @date 2022-04-28 16:40:08
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupDetail {
    private String avatar;
    private String createdAt;
    private long groupId;
    private String groupName;
    private String profile;
    private List<String> notice = new ArrayList<>();
    private boolean isManager;
    private String managerNickname;
    // 用户是否设置群消息免打扰
    private boolean isDisturb;
    // 用户的群昵称
    private String visitCard;

    public GroupDetail(ChatGroup chatGroup, boolean manager, String managerNickname) {
        this.avatar = chatGroup.getAvatar();
        this.createdAt = DateTimeConverter.format(chatGroup.getCreateTime());
        this.groupId = chatGroup.getId();
        this.groupName = chatGroup.getName();
        this.profile = chatGroup.getProfile();
        this.isManager = manager;
        this.managerNickname = managerNickname;
        this.isDisturb = true;
        this.visitCard = "大脸猫";
    }
}
