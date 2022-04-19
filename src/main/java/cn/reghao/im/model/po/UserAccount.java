package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-18 10:59:35
 */
@Getter
@Setter
public class UserAccount extends BaseObject<Integer> {
    private Long userId;
    private String mobile;
    private String password;
    private String platform;
}
