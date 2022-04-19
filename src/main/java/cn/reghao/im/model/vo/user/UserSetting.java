package cn.reghao.im.model.vo.user;

import cn.reghao.im.model.po.Setting;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:02:30
 */
@Data
public class UserSetting {
    private Setting setting;
    private UserInfo userInfo;
}
