package cn.reghao.im.model.po.contact;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-18 10:59:35
 */
@Getter
@Setter
public class UserContact extends BaseObject<Integer> {
    private Long userId;
    private Long friendId;
    private String remark;
    private int status;

    public UserContact() {
        this.status = 2;
    }
}
