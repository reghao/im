package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;

/**
 * @author reghao
 * @date 2022-04-21 20:49:10
 */
public class ChatGroupMember extends BaseObject<Integer> {
    private Long groupId;
    private Long userId;

    public ChatGroupMember(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }
}
