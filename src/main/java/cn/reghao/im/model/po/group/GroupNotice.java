package cn.reghao.im.model.po.group;

import cn.reghao.im.model.dto.group.EditGroupNotice;
import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-28 17:11:09
 */
@NoArgsConstructor
@Getter
public class GroupNotice extends BaseObject<Integer> {
    private Integer noticeId;
    private Integer groupId;
    private String title;
    private String content;
    private Boolean top;
    private Boolean confirmed;
    private Long createdBy;

    public GroupNotice(EditGroupNotice editGroupNotice, long userId) {
        this.noticeId = editGroupNotice.getNoticeId();
        this.groupId = editGroupNotice.getGroupId();
        this.title = editGroupNotice.getTitle();
        this.content = editGroupNotice.getContent();
        this.top = editGroupNotice.isTop();
        this.confirmed = editGroupNotice.isConfirm();
        this.createdBy = userId;
    }
}
