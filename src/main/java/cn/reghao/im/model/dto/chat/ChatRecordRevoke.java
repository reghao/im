package cn.reghao.im.model.dto.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-21 15:41:17
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChatRecordRevoke implements Serializable {
    private static final long serialVersionUID = 1L;

    private long recordId;
}
