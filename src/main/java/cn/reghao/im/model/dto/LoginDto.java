package cn.reghao.im.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-16 21:53:02
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mobile;
    private String password;
    private String platform;
}
