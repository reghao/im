package cn.reghao.im.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-18 14:34:47
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactAdd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long friendId;
    private String remark;
}
