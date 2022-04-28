package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.dto.contact.*;
import cn.reghao.im.model.dto.group.*;
import cn.reghao.im.model.po.contact.ChatGroup;
import cn.reghao.im.model.po.contact.ChatGroupMember;
import cn.reghao.im.model.dto.user.UserInfo;
import cn.reghao.im.model.po.group.GroupNotice;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.util.WebResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {
    private final UserContactMapper userContactMapper;
    private final ChatGroupMapper chatGroupMapper;
    private final ChatGroupMemberMapper chatGroupMemberMapper;
    private final GroupNoticeMapper groupNoticeMapper;
    private final UserProfileMapper userProfileMapper;

    public GroupController(UserContactMapper userContactMapper, ChatGroupMapper chatGroupMapper,
                           ChatGroupMemberMapper chatGroupMemberMapper, GroupNoticeMapper groupNoticeMapper,
                           UserProfileMapper userProfileMapper) {
        this.userContactMapper = userContactMapper;
        this.chatGroupMapper = chatGroupMapper;
        this.chatGroupMemberMapper = chatGroupMemberMapper;
        this.groupNoticeMapper = groupNoticeMapper;
        this.userProfileMapper = userProfileMapper;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupList() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ChatGroup> list = chatGroupMapper.findByGroupIds(userId);
        List<GroupInfo> rows = list.stream().map(GroupInfo::new).collect(Collectors.toList());
        Map<String, List<GroupInfo>> map = new HashMap<>();
        map.put("rows", rows);
        return WebResult.success(map);
    }

    @ApiOperation(value = "群组详细信息")
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDetail(@RequestParam("group_id") int groupId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());

        ChatGroup chatGroup = chatGroupMapper.findByGroupId(groupId);
        long ownerId = chatGroup.getOwnerId();
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(ownerId);
        boolean manager = ownerId == userId;
        String nickname = userInfo.getNickname();

        GroupDetail groupDetail = new GroupDetail(chatGroup, manager, nickname);
        return WebResult.success(groupDetail);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupCreate(@RequestBody CreateGroup createGroup) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatGroup chatGroup = new ChatGroup(createGroup, userId);
        chatGroupMapper.save(chatGroup);

        long groupId = chatGroup.getId();
        List<ChatGroupMember> list = Arrays.stream(createGroup.getIds().split(","))
                .map(memberId -> new ChatGroupMember(groupId, Long.valueOf(memberId)))
                .collect(Collectors.toList());
        list.add(new ChatGroupMember(groupId, userId));
        chatGroupMemberMapper.saveAll(list);

        Map<String, Long> map = new HashMap<>();
        map.put("group_id", groupId);
        return WebResult.success(map);
    }

    @PostMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSetting(@RequestBody GroupSetting groupSetting) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());

        int groupId = groupSetting.getGroupId();
        ChatGroup chatGroup = chatGroupMapper.findByGroupId(groupId);
        long ownerId = chatGroup.getOwnerId();
        if (userId != ownerId) {
            return WebResult.failWithMsg("没有权限");
        }

        chatGroup.setName(groupSetting.getGroupName());
        chatGroup.setProfile(groupSetting.getProfile());
        chatGroup.setAvatar(groupSetting.getAvatar());
        chatGroupMapper.updateChatGroup(chatGroup);

        return WebResult.success();
    }

    @ApiOperation(value = "(群主)邀请用户入群")
    @PostMapping(value = "/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupInvite(@RequestBody GroupInvite groupInvite) {
        long groupId = groupInvite.getGroupId();
        List<ChatGroupMember> list = Arrays.stream(groupInvite.getIds().split(","))
                .map(memberId -> new ChatGroupMember(groupId, Long.valueOf(memberId)))
                .collect(Collectors.toList());
        chatGroupMemberMapper.saveAll(list);
        return WebResult.success();
    }

    @ApiOperation(value = "(群主)移除群成员")
    @PostMapping(value = "/member/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberRemove(@RequestBody RemoveMember removeMember) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long groupId = removeMember.getGroupId();
        ChatGroup chatGroup = chatGroupMapper.findByGroupId(groupId);
        long ownerId = chatGroup.getOwnerId();
        if (userId != ownerId) {
            return WebResult.failWithMsg("没有权限");
        }

        List<Long> userIds = Arrays.stream(removeMember.getMembersIds().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        return WebResult.success();
    }

    @ApiOperation(value = "(群主)解散群组")
    @PostMapping(value = "/dismiss", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDismiss() {
        return WebResult.success();
    }

    @ApiOperation(value = "(群成员)退出群组")
    @PostMapping(value = "/secede", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSecede(@RequestBody SecedeGroup secedeGroup) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long groupId = secedeGroup.getGroupId();
        ChatGroup chatGroup = chatGroupMapper.findByGroupId(groupId);
        long ownerId = chatGroup.getOwnerId();
        if (userId == ownerId) {
            return WebResult.failWithMsg("群主请先解散群");
        }

        return WebResult.success();
    }

    @ApiOperation(value = "修改群组内显示的昵称")
    @PostMapping(value = "/member/remark", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberRemark() {
        return WebResult.success();
    }

    @GetMapping(value = "/member/invites", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberInvites(@RequestParam("group_id") long groupId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        if (groupId != 0) {
        }

        List<ContactInfo> list = userContactMapper.findByUserId(userId);
        return WebResult.success(list);
    }

    @ApiOperation(value = "群成员")
    @GetMapping(value = "/member/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberList(@RequestParam("group_id") int groupId) {
        List<Long> userIds = chatGroupMapper.findUserIdsByGroupId(groupId);
        List<GroupMemberInfo> memberList = userIds.stream().map(memberId -> {
            UserInfo userInfo = userProfileMapper.findUserInfoByUserId(memberId);
            return new GroupMemberInfo(userInfo);
        }).collect(Collectors.toList());

        return WebResult.success(memberList);
    }

    @ApiOperation(value = "群公告")
    @GetMapping(value = "/notice/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupNoticeList(@RequestParam("group_id") int groupId) {
        List<GroupNotice> rows = groupNoticeMapper.findByGroupId(groupId);
        Map<String, List<GroupNotice>> map = new HashMap<>();
        map.put("rows", rows);
        return WebResult.success(map);
    }

    @ApiOperation(value = "添加/修改群公告")
    @PostMapping(value = "/notice/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupNoticeEdit(@RequestBody EditGroupNotice editGroupNotice) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());

        int noticeId = editGroupNotice.getNoticeId();
        GroupNotice groupNotice = new GroupNotice(editGroupNotice, userId);
        if (noticeId == 0) {
            groupNoticeMapper.save(groupNotice);
        } else {
            groupNoticeMapper.updateSetNotice(groupNotice);
        }

        return WebResult.success();
    }
}
