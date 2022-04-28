package cn.reghao.im.model.dto.contact;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-18 14:34:47
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactAddRespond implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long applyId;
    private String remark;
}
