package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-28 16:40:08
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class GroupDetailRet {
    private long groupId;
    private String groupName;
    private String avatar;
    private String profile;
    private String createdAt;
    private NoticeRet notice;

    private long ownerId;
    private String managerNickname;
    private boolean isManager;
    // 用户是否设置群消息免打扰
    private boolean isDisturb;
    // 用户的群昵称
    private String visitCard;
}
