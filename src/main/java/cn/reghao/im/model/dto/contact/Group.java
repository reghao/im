package cn.reghao.im.model.dto.contact;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 22:57:06
 */
@Data
public class Group {
    private String avatar;
    private String groupName;
    private long id;
    private boolean isDisturb;
    private int leader;
    private String profile;
}
