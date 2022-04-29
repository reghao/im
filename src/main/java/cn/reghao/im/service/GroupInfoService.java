package cn.reghao.im.service;

import cn.reghao.im.db.mapper.GroupInfoMapper;
import cn.reghao.im.db.mapper.GroupMemberMapper;
import cn.reghao.im.db.mapper.GroupNoticeMapper;
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
 * @date 2022-04-29 11:18:27
 */
@Service
public class GroupInfoService {
    private final GroupInfoMapper groupInfoMapper;
    private final GroupMemberMapper groupMemberMapper;
    private final GroupNoticeMapper groupNoticeMapper;

    public GroupInfoService(GroupInfoMapper groupInfoMapper, GroupMemberMapper groupMemberMapper,
                            GroupNoticeMapper groupNoticeMapper) {
        this.groupInfoMapper = groupInfoMapper;
        this.groupMemberMapper = groupMemberMapper;
        this.groupNoticeMapper = groupNoticeMapper;
    }

    public CreateGroupRet createGroup(CreateGroup createGroup) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        GroupInfo groupInfo = new GroupInfo(createGroup, userId);
        groupInfoMapper.save(groupInfo);

        long groupId = groupInfo.getId();
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

        GroupDetailRet groupDetailRet = groupInfoMapper.findDetailByGroupId(groupId);
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
        GroupInfo groupInfo = groupInfoMapper.findByGroupId(groupId);
        long ownerId = groupInfo.getOwnerId();
        if (userId != ownerId) {
            // TODO 只有群主才可以修改群信息
            return;
        }

        groupInfo.setName(groupSetting.getGroupName());
        groupInfo.setProfile(groupSetting.getProfile());
        groupInfo.setAvatar(groupSetting.getAvatar());
        groupInfoMapper.updateGroupInfo(groupInfo);
    }

    public void deleteGroup(long groupId) {
    }
}
