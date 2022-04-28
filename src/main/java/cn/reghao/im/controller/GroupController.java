package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.ChatGroupMapper;
import cn.reghao.im.db.mapper.ChatGroupMemberMapper;
import cn.reghao.im.db.mapper.UserContactMapper;
import cn.reghao.im.model.dto.CreateGroup;
import cn.reghao.im.model.po.ChatGroup;
import cn.reghao.im.model.vo.contact.ContactInfo;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.util.WebResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {
    private UserContactMapper userContactMapper;
    private ChatGroupMapper chatGroupMapper;
    private ChatGroupMemberMapper chatGroupMemberMapper;

    public GroupController(UserContactMapper userContactMapper, ChatGroupMapper chatGroupMapper,
                           ChatGroupMemberMapper chatGroupMemberMapper) {
        this.userContactMapper = userContactMapper;
        this.chatGroupMapper = chatGroupMapper;
        this.chatGroupMemberMapper = chatGroupMemberMapper;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupList() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<UserInfo> rows = new ArrayList<>();
        Map<String, List<UserInfo>> map = new HashMap<>();
        map.put("rows", rows);
        return WebResult.success(map);
    }

    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDetail() {
        return WebResult.success();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupCreate(@RequestBody CreateGroup createGroup) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatGroup chatGroup = new ChatGroup(createGroup, userId);
        chatGroupMapper.save(chatGroup);

        long groupId = chatGroup.getId();
        Map<String, Long> map = new HashMap<>();
        map.put("group_id", groupId);
        return WebResult.success(map);
    }

    @PostMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSetting() {
        return WebResult.success();
    }

    @PostMapping(value = "/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupInvite() {
        return WebResult.success();
    }

    @PostMapping(value = "/member/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberRemove() {
        return WebResult.success();
    }

    @PostMapping(value = "/dismiss", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDismiss() {
        return WebResult.success();
    }

    @PostMapping(value = "/secede", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupSecede() {
        return WebResult.success();
    }

    @PostMapping(value = "/member/remark", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberRemark() {
        return WebResult.success();
    }

    @GetMapping(value = "/member/invites", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberInvites(@RequestParam("group_id") long groupId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ContactInfo> list = userContactMapper.findByUserId(userId);
        return WebResult.success(list);
    }

    @GetMapping(value = "/member/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupMemberList() {
        return WebResult.success();
    }

    @GetMapping(value = "/notice/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupNoticeList() {
        return WebResult.success();
    }

    @PostMapping(value = "/notice/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupNoticeEdit() {
        return WebResult.success();
    }
}
