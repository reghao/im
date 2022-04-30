package cn.reghao.im.model.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-16 22:34:59
 */
@Getter
@Setter
public class ChatRecordGet implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long record_id;
    private Long receiver_id;
    private Integer talk_type;
    private Integer msg_type;
    private Integer limit;
}
