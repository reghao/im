package cn.reghao.im.util;

import lombok.Data;

import java.util.Date;

/**
 * @author reghao
 * @date 2021-07-26 09:58:45
 */
@Data
public class JwtPayload {
    private String userId;
    private String roles;
    private Date expiration;
    private String signKey;

    public JwtPayload(String userId, String roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public JwtPayload(String userId, String roles, Date expiration) {
        this.userId = userId;
        this.roles = roles;
        this.expiration = expiration;
    }
}
