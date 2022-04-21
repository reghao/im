package cn.reghao.im.model.po;

import cn.reghao.im.model.dto.CreateGroup;
import cn.reghao.jutil.jdk.db.BaseObject;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-21 20:49:10
 */
public class ChatGroup extends BaseObject<Integer> {
    private long groupId;
    private String name;
    private String avatar;
    private String profile;
    private long ownerId;
    private List<Long> memberIds;

    public ChatGroup(CreateGroup createGroup, long userId) {
        this.name = createGroup.getName();
        this.avatar = createGroup.getAvatar();
        this.profile = createGroup.getProfile();
        this.ownerId = userId;
    }
}
