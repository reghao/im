package cn.reghao.im.model.dto.group;

import cn.reghao.im.model.dto.user.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author reghao
 * @date 2022-04-28 16:45:58
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupMemberInfo {
    private String avatar;
    private int gender;
    private long id;
    private long leader;
    private String motto;
    private String nickname;
    private String userCard;
    private long userId;

    public GroupMemberInfo(UserInfo userInfo) {
        this.avatar = userInfo.getAvatar();
        this.gender = userInfo.getGender();
        this.motto = userInfo.getMotto();
        this.nickname = userInfo.getNickname();
        this.userCard = "小糊涂神";
        this.userId = userInfo.getUid();
        this.leader = 2;
        this.id = userInfo.getUid();
    }
}
