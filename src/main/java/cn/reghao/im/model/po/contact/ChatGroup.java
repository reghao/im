package cn.reghao.im.model.po.contact;

import cn.reghao.im.model.dto.contact.CreateGroup;
import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-21 20:49:10
 */
@Getter
public class ChatGroup extends BaseObject<Integer> {
    private long groupId;
    private final String name;
    private final String avatar;
    private final String profile;
    private final long ownerId;
    private List<Long> memberIds;

    public ChatGroup(CreateGroup createGroup, long userId) {
        this.name = createGroup.getName();
        this.avatar = createGroup.getAvatar();
        this.profile = createGroup.getProfile();
        this.ownerId = userId;
    }
}
