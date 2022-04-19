package cn.reghao.im.model.dto;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-18 11:21:58
 */
@Data
public class SmsCode {
    private String channel;
    private String mobile;
}
