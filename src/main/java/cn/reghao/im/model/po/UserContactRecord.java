package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-18 15:31:53
 */
@Getter
@Setter
public class UserContactRecord extends BaseObject<Integer> {
    private Long requestUserId;
    private Long addedUserId;
    // 1- 非好友, 2 - 已是好友
    private int friendStatus;
    // 0 - 添加联系人, 1 - 已发送添加申请, 2 - 已通过添加申请
    private int friendApply;
    private String remark;
}
