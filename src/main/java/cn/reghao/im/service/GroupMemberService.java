package cn.reghao.im.service;

import cn.reghao.im.db.mapper.ChatDialogMapper;
import cn.reghao.im.db.mapper.GroupInfoMapper;
import cn.reghao.im.db.mapper.GroupMemberMapper;
import cn.reghao.im.db.mapper.UserContactMapper;
import cn.reghao.im.model.dto.contact.ContactInfo;
import cn.reghao.im.model.dto.group.*;
import cn.reghao.im.model.po.group.GroupInfo;
import cn.reghao.im.model.po.group.GroupMember;
import cn.reghao.im.util.Jwt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-04-29 11:22:13
 */
@Service
public class GroupMemberService {
    private final GroupMemberMapper groupMemberMapper;
    private final GroupInfoMapper groupInfoMapper;
    private final UserContactMapper userContactMapper;
    private final ChatDialogMapper chatDialogMapper;

    public GroupMemberService(GroupMemberMapper groupMemberMapper, GroupInfoMapper groupInfoMapper,
                              UserContactMapper userContactMapper, ChatDialogMapper chatDialogMapper) {
        this.groupMemberMapper = groupMemberMapper;
        this.groupInfoMapper = groupInfoMapper;
        this.userContactMapper = userContactMapper;
        this.chatDialogMapper = chatDialogMapper;
    }

    public GroupInfoRetList getGroups() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<GroupInfoRet> rows = groupInfoMapper.findGroupsByUserId(userId);
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

    public List<ContactInfo> getInvitedUsers(long groupId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ContactInfo> list;
        if (groupId == 0) {
            list = userContactMapper.findByUserId(userId);
        } else {
            list = userContactMapper.findFriendsByNotInGroup(userId, groupId);
        }

        return list;
    }

    public void inviteUsers(GroupInvite groupInvite) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long groupId = groupInvite.getGroupId();
        GroupMember groupMember = groupMemberMapper.findByGroupAndUserId(groupId, userId);
        if (groupMember == null) {
            // TODO 非本群用户不能邀请其他用户进群
            return;
        }

        List<GroupMember> list = Arrays.stream(groupInvite.getIds().split(","))
                .map(memberId -> new GroupMember(groupId, Long.valueOf(memberId)))
                .collect(Collectors.toList());
        groupMemberMapper.saveAll(list);
    }

    public void leaveGroup(SecedeGroup secedeGroup) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long groupId = secedeGroup.getGroupId();
        GroupInfo groupInfo = groupInfoMapper.findByGroupId(groupId);
        long ownerId = groupInfo.getOwnerId();
        if (userId == ownerId) {
            // TODO 群主请先解散群
            return;
        }

        groupMemberMapper.deleteGroupMembers(groupId, List.of(userId));
        chatDialogMapper.deleteGroupChatDialog(groupId, List.of(userId));
    }

    public void removeMembers(RemoveMember removeMember) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long groupId = removeMember.getGroupId();
        GroupInfo groupInfo = groupInfoMapper.findByGroupId(groupId);
        long ownerId = groupInfo.getOwnerId();
        if (userId != ownerId) {
            // TODO 没有权限, 只有群主才能移除群成员
            return;
        }

        List<Long> userIds = Arrays.stream(removeMember.getMembersIds().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        groupMemberMapper.deleteGroupMembers(groupId, userIds);
        chatDialogMapper.deleteGroupChatDialog(groupId, userIds);
    }
}
