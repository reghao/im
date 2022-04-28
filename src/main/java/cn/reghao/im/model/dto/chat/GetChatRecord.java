package cn.reghao.im.model.dto.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-16 22:34:59
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetChatRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long recordId;
    private Long receiverId;
    private Integer talkType;
    private Integer limit;
}
