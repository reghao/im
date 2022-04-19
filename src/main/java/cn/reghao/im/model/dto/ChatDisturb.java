package cn.reghao.im.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-17 16:41:48
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChatDisturb {
    private boolean isDisturb;
    private int receiverId;
    private int talkType;
}
