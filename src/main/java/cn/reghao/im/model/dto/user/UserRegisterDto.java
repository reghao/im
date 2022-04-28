package cn.reghao.im.model.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-18 10:52:15
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserRegisterDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mobile;
    private String nickname;
    private String password;
    private String platform;
    private String smsCode;
}
