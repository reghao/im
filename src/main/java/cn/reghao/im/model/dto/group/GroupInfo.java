package cn.reghao.im.model.dto.group;

import cn.reghao.im.model.po.contact.ChatGroup;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:57:06
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupInfo {
    private String avatar;
    private String groupName;
    private long id;
    private boolean isDisturb;
    private long leader;
    private String profile;

    public GroupInfo(ChatGroup chatGroup) {
        this.avatar = chatGroup.getAvatar();
        this.groupName = chatGroup.getName();
        this.id = chatGroup.getId();
        this.leader = chatGroup.getOwnerId();
        this.profile = chatGroup.getProfile();
        this.isDisturb = false;
    }
}
