package cn.reghao.im.controller;

import cn.reghao.im.util.WebResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupList() {
        return WebResult.success();
    }

    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupDetail() {
        return WebResult.success();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String groupCreate() {
        return WebResult.success();
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
    public String groupMemberInvites() {
        return WebResult.success();
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
