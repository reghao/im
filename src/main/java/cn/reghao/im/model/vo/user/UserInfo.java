package cn.reghao.im.model.vo.user;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:03:22
 */
@Data
public class UserInfo {
    private long id;
    private long userId;
    private long uid;
    private String nickname;
    private int gender;
    private String motto;
    private String signature;
    private String avatar;
    private int isOnline;
}
