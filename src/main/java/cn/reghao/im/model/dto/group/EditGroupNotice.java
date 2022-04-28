package cn.reghao.im.model.dto.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-28 17:05:44
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class EditGroupNotice {
    private int groupId;
    private int noticeId;
    private String title;
    private String content;
    private boolean isTop;
    private boolean isConfirm;
}
