package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.dto.contact.*;
import cn.reghao.im.model.dto.group.*;
import cn.reghao.im.model.po.group.ChatGroup;
import cn.reghao.im.model.po.group.GroupMember;
import cn.reghao.im.service.ChatGroupService;
import cn.reghao.im.service.GroupMemberService;
import cn.reghao.im.service.GroupNoticeService;
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
    private final GroupMemberMapper groupMemberMapper;
    private final ChatGroupService chatGroupService;
    private final GroupMemberService groupMemberService;
    private final GroupNoticeService groupNoticeService;

    public GroupController(UserContactMapper userContactMapper, ChatGroupMapper chatGroupMapper,
                           GroupMemberMapper groupMemberMapper,
                           ChatGroupService chatGroupService, GroupMemberService groupMemberService,
                           GroupNoticeService groupNoticeService) {
        this.userContactMapper = userContactMapper;
        this.chatGroupMapper = chatGroupMapper;
        this.groupMemberMapper = groupMemberMapper;
        this.chatGroupService = chatGroupService;
        this.groupMemberService = groupMemberService;
        this.groupNoticeService = groupNoticeService;
    }

    @ApiOperation(value = "创建群组")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupCreate(@RequestBody CreateGroup createGroup) {
        CreateGroupRet createGroupRet = chatGroupService.createGroup(createGroup);
        return WebResult.success(createGroupRet);
    }

    @ApiOperation(value = "群组详细信息")
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDetail(@RequestParam("group_id") int groupId) {
        GroupDetailRet groupDetailRet = chatGroupService.getGroupDetail(groupId);
        return WebResult.success(groupDetailRet);
    }

    @ApiOperation(value = "群组信息设置")
    @PostMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSetting(@RequestBody GroupSetting groupSetting) {
        chatGroupService.editGroupDetail(groupSetting);
        return WebResult.success();
    }

    @ApiOperation(value = "(群主)解散群组")
    @PostMapping(value = "/dismiss", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDismiss() {
        // TODO 前端未实现该接口
        return WebResult.success();
    }

    @ApiOperation(value = "用户所在的群组")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupList() {
        GroupInfoRetList groupInfoRetList = groupMemberService.getGroups();
        return WebResult.success(groupInfoRetList);
    }

    @ApiOperation(value = "群成员")
    @GetMapping(value = "/member/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberList(@RequestParam("group_id") long groupId) {
        List<GroupMemberRet> memberList = groupMemberService.getGroupMember(groupId);
        return WebResult.success(memberList);
    }

    @ApiOperation(value = "(群成员)邀请用户入群")
    @PostMapping(value = "/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupInvite(@RequestBody GroupInvite groupInvite) {
        long groupId = groupInvite.getGroupId();
        List<GroupMember> list = Arrays.stream(groupInvite.getIds().split(","))
                .map(memberId -> new GroupMember(groupId, Long.valueOf(memberId)))
                .collect(Collectors.toList());
        groupMemberMapper.saveAll(list);
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
    public String groupMemberRemark(@RequestBody MemberRemark memberRemark) {
        groupMemberService.setNicknameInGroup(memberRemark);
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

    @ApiOperation(value = "群公告")
    @GetMapping(value = "/notice/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupNoticeList(@RequestParam("group_id") int groupId) {
        NoticeListRet noticeListRet = groupNoticeService.getNoticeList(groupId);
        return WebResult.success(noticeListRet);
    }

    @ApiOperation(value = "添加/修改群公告")
    @PostMapping(value = "/notice/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupNoticeEdit(@RequestBody EditGroupNotice editGroupNotice) {
        groupNoticeService.createOrUpdateNotice(editGroupNotice);
        return WebResult.success();
    }
}
