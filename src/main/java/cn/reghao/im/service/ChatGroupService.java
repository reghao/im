package cn.reghao.im.service;

import cn.reghao.im.db.mapper.ChatGroupMapper;
import cn.reghao.im.db.mapper.GroupMemberMapper;
import cn.reghao.im.db.mapper.GroupNoticeMapper;
import cn.reghao.im.model.dto.group.*;
import cn.reghao.im.model.po.group.ChatGroup;
import cn.reghao.im.model.po.group.GroupMember;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.util.WebResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-04-29 11:18:27
 */
@Service
public class ChatGroupService {
    private final ChatGroupMapper chatGroupMapper;
    private final GroupMemberMapper groupMemberMapper;
    private final GroupNoticeMapper groupNoticeMapper;

    public ChatGroupService(ChatGroupMapper chatGroupMapper, GroupMemberMapper groupMemberMapper,
                            GroupNoticeMapper groupNoticeMapper) {
        this.chatGroupMapper = chatGroupMapper;
        this.groupMemberMapper = groupMemberMapper;
        this.groupNoticeMapper = groupNoticeMapper;
    }

    public CreateGroupRet createGroup(CreateGroup createGroup) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatGroup chatGroup = new ChatGroup(createGroup, userId);
        chatGroupMapper.save(chatGroup);

        long groupId = chatGroup.getId();
        List<GroupMember> list = Arrays.stream(createGroup.getIds().split(","))
                .map(memberId -> new GroupMember(groupId, Long.valueOf(memberId)))
                .collect(Collectors.toList());
        list.add(new GroupMember(groupId, userId));
        groupMemberMapper.saveAll(list);
        return new CreateGroupRet(groupId);
    }

    public GroupDetailRet getGroupDetail(long groupId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        GroupMember groupMember = groupMemberMapper.findByGroupAndUserId(groupId, userId);
        if (groupMember == null) {
            // TODO 只有群组成员才可获取群信息
            return null;
        }

        GroupDetailRet groupDetailRet = chatGroupMapper.findDetailByGroupId(groupId);
        groupDetailRet.setVisitCard(groupMember.getNickname());
        long ownerId = groupDetailRet.getOwnerId();
        if (ownerId == userId) {
            groupDetailRet.setManager(true);
        }

        NoticeRet noticeRet = groupNoticeMapper.findByLatest(groupId);
        groupDetailRet.setNotice(noticeRet);
        return groupDetailRet;
    }

    public void editGroupDetail(GroupSetting groupSetting) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        int groupId = groupSetting.getGroupId();
        ChatGroup chatGroup = chatGroupMapper.findByGroupId(groupId);
        long ownerId = chatGroup.getOwnerId();
        if (userId != ownerId) {
            // TODO 只有群主才可以修改群信息
            return;
        }

        chatGroup.setName(groupSetting.getGroupName());
        chatGroup.setProfile(groupSetting.getProfile());
        chatGroup.setAvatar(groupSetting.getAvatar());
        chatGroupMapper.updateChatGroup(chatGroup);
    }

    public void deleteGroup(long groupId) {
    }
}
