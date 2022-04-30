package cn.reghao.im.model.dto.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-16 22:33:33
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChatUnreadClear implements Serializable {
    private static final long serialVersionUID = 1L;

    private long receiverId;
    private int talkType;
}
