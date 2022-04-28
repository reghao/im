package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-28 16:52:08
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class GroupInvite {
    private int groupId;
    private String ids;
}
