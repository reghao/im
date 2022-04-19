package cn.reghao.im.model.dto.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-17 16:56:34
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CodeBlockMsg extends ChatMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lang;
    private String code;
}
