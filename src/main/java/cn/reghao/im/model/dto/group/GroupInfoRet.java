package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:57:06
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupInfoRet {
    private long id;
    private String groupName;
    private String avatar;
    private String groupProfile;
    // 0 - 加入群, 1 - 管理群, 2 - 创建群
    private int leader;
    // 1 - 开启免打扰, 2 - 关闭免打扰
    private int isDisturb;
    private boolean owner;
    private boolean disturb;
}
