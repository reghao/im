package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-28 16:45:58
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class GroupMemberRet {
    private long id;
    private long userId;
    private String nickname;
    private String avatar;
    private int gender;
    private String motto;

    private boolean owner;
    private int leader;
    private String userCard;
}
