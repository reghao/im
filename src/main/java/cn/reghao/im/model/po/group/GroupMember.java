package cn.reghao.im.model.po.group;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-21 20:49:10
 */
@NoArgsConstructor
@Getter
public class GroupMember extends BaseObject<Integer> {
    private Long groupId;
    private Long userId;
    private String nickname;
    private boolean owner;
    private boolean disturb;

    public GroupMember(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
        this.owner = false;
        this.disturb = false;
    }
}
