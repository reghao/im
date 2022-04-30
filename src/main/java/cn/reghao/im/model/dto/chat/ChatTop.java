package cn.reghao.im.model.dto.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-28 19:02:59
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class ChatTop {
    private int type;
    private int listId;
}
