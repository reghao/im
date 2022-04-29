package cn.reghao.im.controller;

import cn.reghao.im.model.dto.contact.*;
import cn.reghao.im.model.dto.group.*;
import cn.reghao.im.service.GroupInfoService;
import cn.reghao.im.service.GroupMemberService;
import cn.reghao.im.service.GroupNoticeService;
import cn.reghao.im.util.WebResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupInfoService groupInfoService;
    private final GroupMemberService groupMemberService;
    private final GroupNoticeService groupNoticeService;

    public GroupController(GroupInfoService groupInfoService, GroupMemberService groupMemberService,
                           GroupNoticeService groupNoticeService) {
        this.groupInfoService = groupInfoService;
        this.groupMemberService = groupMemberService;
        this.groupNoticeService = groupNoticeService;
    }

    @ApiOperation(value = "创建群组")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupCreate(@RequestBody CreateGroup createGroup) {
        CreateGroupRet createGroupRet = groupInfoService.createGroup(createGroup);
        return WebResult.success(createGroupRet);
    }

    @ApiOperation(value = "群组详细信息")
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDetail(@RequestParam("group_id") int groupId) {
        GroupDetailRet groupDetailRet = groupInfoService.getGroupDetail(groupId);
        return WebResult.success(groupDetailRet);
    }

    @ApiOperation(value = "群组信息设置")
    @PostMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSetting(@RequestBody GroupSetting groupSetting) {
        groupInfoService.editGroupDetail(groupSetting);
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

    @ApiOperation(value = "获取需要邀请入群的用户")
    @GetMapping(value = "/member/invites", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberInvites(@RequestParam("group_id") long groupId) {
        List<ContactInfo> list = groupMemberService.getInvitedUsers(groupId);
        return WebResult.success(list);
    }

    @ApiOperation(value = "邀请用户进群")
    @PostMapping(value = "/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupInvite(@RequestBody GroupInvite groupInvite) {
        groupMemberService.inviteUsers(groupInvite);
        return WebResult.success();
    }

    @ApiOperation(value = "(群成员)退出群组")
    @PostMapping(value = "/secede", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSecede(@RequestBody SecedeGroup secedeGroup) {
        groupMemberService.leaveGroup(secedeGroup);
        return WebResult.success();
    }

    @ApiOperation(value = "(群主)移除群成员")
    @PostMapping(value = "/member/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberRemove(@RequestBody RemoveMember removeMember) {
        groupMemberService.removeMembers(removeMember);
        return WebResult.success();
    }

    @ApiOperation(value = "修改群组内显示的昵称")
    @PostMapping(value = "/member/remark", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberRemark(@RequestBody MemberRemark memberRemark) {
        groupMemberService.setNicknameInGroup(memberRemark);
        return WebResult.success();
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
