package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-18 13:23:35
 */
@Getter
@Setter
public class UserProfile extends BaseObject<Integer> {
    private Long userId;
    private String nickname;
    private int gender;
    private String motto;
    private String avatar;
    private boolean online;

    public UserProfile() {
        this.gender = 1;
        this.avatar = "https://pic2.zhimg.com/v2-e5842374b2257b981188c33391b188ad_xll.jpg";
        this.online = false;
    }
}
