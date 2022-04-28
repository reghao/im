package cn.reghao.im.model.dto.message;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:37:18
 */
@Data
public class LoginRecord {
    private String address;
    private String agent;
    private String createdAt;
    private String ip;
    private String platform;
    private String reason;
}
