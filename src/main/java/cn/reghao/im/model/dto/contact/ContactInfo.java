package cn.reghao.im.model.dto.contact;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 23:04:42
 */
@Data
public class ContactInfo {
    private long id;
    private String nickname;
    private int gender;
    private String motto;
    private String avatar;
    private String friendRemark;
    private boolean isOnline;
}
