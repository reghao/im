package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-29 18:38:22
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class MemberRemark {
    private long groupId;
    private String visitCard;
}
