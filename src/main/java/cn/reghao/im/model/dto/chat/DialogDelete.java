package cn.reghao.im.model.dto.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-29 11:05:10
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class DialogDelete {
    private int listId;
}
