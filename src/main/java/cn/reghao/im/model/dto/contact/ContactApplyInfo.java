package cn.reghao.im.model.dto.contact;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-17 15:54:07
 */
@Data
public class ContactApplyInfo {
    private long id;
    private long userId;
    private int friendId;
    private String nickname;
    private String avatar;
    private String remark;
    private String createdAt;
}
