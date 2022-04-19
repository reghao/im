package cn.reghao.im.model.vo.contact;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-16 23:04:42
 */
@Data
public class SearchContactResult {
    private long id;
    private String mobile;
    private String nickname;
    private int gender;
    private String motto;
    private String avatar;
}
