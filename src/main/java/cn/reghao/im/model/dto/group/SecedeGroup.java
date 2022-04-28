package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-28 17:41:28
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class SecedeGroup {
    private Long groupId;
}
