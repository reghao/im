package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author reghao
 * @date 2022-04-29 11:32:07
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NoticeRet {
    private int id;
    private String title;
    private String content;
    private boolean isConfirm;
    private boolean isTop;
    private String createdAt;
    private String updatedAt;

    private long userId;
    private String nickname;
    private String avatar;
}
