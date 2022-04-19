package cn.reghao.im.model.dto.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-17 17:15:14
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ForwardMsg {
    private int forwardMode;
    private int receiverId;
    private int receiverUserIds;
    private int receiverGroupIds;
    private int recordIds;
    private int talkType;
}
