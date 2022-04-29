package cn.reghao.im.service;

import cn.reghao.im.db.mapper.ChatGroupMapper;
import cn.reghao.im.db.mapper.GroupMemberMapper;
import cn.reghao.im.model.dto.group.GroupInfoRet;
import cn.reghao.im.model.dto.group.GroupInfoRetList;
import cn.reghao.im.model.dto.group.GroupMemberRet;
import cn.reghao.im.model.dto.group.MemberRemark;
import cn.reghao.im.util.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-29 11:22:13
 */
@Service
public class GroupMemberService {
    private final GroupMemberMapper groupMemberMapper;
    private final ChatGroupMapper chatGroupMapper;

    public GroupMemberService(GroupMemberMapper groupMemberMapper, ChatGroupMapper chatGroupMapper) {
        this.groupMemberMapper = groupMemberMapper;
        this.chatGroupMapper = chatGroupMapper;
    }

    public GroupInfoRetList getGroups() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<GroupInfoRet> rows = chatGroupMapper.findGroupsByUserId(userId);
        rows.forEach(groupInfoRet -> {
            if (groupInfoRet.isOwner()) {
                groupInfoRet.setLeader(2);
            } else {
                groupInfoRet.setLeader(0);
            }

            if (groupInfoRet.isDisturb()) {
                groupInfoRet.setIsDisturb(1);
            } else {
                groupInfoRet.setIsDisturb(2);
            }
        });

        return new GroupInfoRetList(rows);
    }

    public List<GroupMemberRet> getGroupMember(long groupId) {
        List<GroupMemberRet> memberList = groupMemberMapper.findByGroupId(groupId);
        memberList.forEach(groupMemberRet -> {
            if (groupMemberRet.isOwner()) {
                groupMemberRet.setLeader(2);
            } else {
                groupMemberRet.setLeader(0);
            }
        });
        return memberList;
    }

    public void setNicknameInGroup(MemberRemark memberRemark) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long groupId = memberRemark.getGroupId();
        String remarkName = memberRemark.getVisitCard();
        groupMemberMapper.updateSetMemberRemark(groupId, userId, remarkName);
    }

    public void leaveGroup(long groupId) {
    }

    public void addMember(long groupId, List<Long> memberIds) {
    }

    public void removeMember(long groupId, List<Long> memberIds) {
    }
}
