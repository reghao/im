package cn.reghao.im.model.dto.auth;

import cn.reghao.im.model.dto.user.UserInfo;
import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 21:53:54
 */
@Data
public class LoginRetDto {
    private String accessToken;
    private long expiresIn;
    private String type;
    private UserInfo userInfo;
}
