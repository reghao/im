package cn.reghao.im.model.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-17 16:56:50
 */
@Getter
@Setter
public class ImageMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private long receiver_id;
    private int talk_type;
    private String upload_id;
}
