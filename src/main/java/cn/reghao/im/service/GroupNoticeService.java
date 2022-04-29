package cn.reghao.im.service;

import cn.reghao.im.db.mapper.GroupMemberMapper;
import cn.reghao.im.db.mapper.GroupNoticeMapper;
import cn.reghao.im.model.dto.group.EditGroupNotice;
import cn.reghao.im.model.dto.group.NoticeListRet;
import cn.reghao.im.model.dto.group.NoticeRet;
import cn.reghao.im.model.po.group.GroupMember;
import cn.reghao.im.model.po.group.GroupNotice;
import cn.reghao.im.util.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reghao
 * @date 2022-04-29 11:23:24
 */
@Service
public class GroupNoticeService {
    private final GroupNoticeMapper groupNoticeMapper;
    private final GroupMemberMapper groupMemberMapper;

    public GroupNoticeService(GroupNoticeMapper groupNoticeMapper, GroupMemberMapper groupMemberMapper) {
        this.groupNoticeMapper = groupNoticeMapper;
        this.groupMemberMapper = groupMemberMapper;
    }

    public void createOrUpdateNotice(EditGroupNotice editGroupNotice) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        int noticeId = editGroupNotice.getNoticeId();
        GroupNotice groupNotice = new GroupNotice(editGroupNotice, userId);
        if (noticeId == 0) {
            groupNoticeMapper.save(groupNotice);
        } else {
            GroupNotice groupNotice1 = groupNoticeMapper.findByNoticeId(noticeId);
            if (groupNotice1.getCreateBy() == userId) {
                groupNoticeMapper.updateSetNotice(groupNotice);
            } else {
                // TODO 群公告只能由创建者修改
            }
        }
    }

    public NoticeListRet getNoticeList(long groupId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        GroupMember groupMember = groupMemberMapper.findByGroupAndUserId(groupId, userId);
        if (groupMember == null) {
            // TODO 不是该组成员, 没有权限查看群公告
            return new NoticeListRet(new ArrayList<>());
        }

        List<NoticeRet> rows = groupNoticeMapper.findByGroupId(groupId);
        return new NoticeListRet(rows);
    }
}
