package cn.reghao.im.controller;

import cn.reghao.im.model.dto.chat.*;
import cn.reghao.im.service.ChatDialogService;
import cn.reghao.im.util.WebResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/talk")
public class ChatDialogController {
    private final ChatDialogService chatDialogService;

    public ChatDialogController(ChatDialogService chatDialogService) {
        this.chatDialogService = chatDialogService;
    }

    @ApiOperation(value = "创建聊天窗口")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkCreate(@RequestBody ChatInitial chatInitial) {
        ChatInitialRet chatInitialRet = chatDialogService.createChatDialog(chatInitial);
        return WebResult.success(chatInitialRet);
    }

    @ApiOperation(value = "获取聊天窗口列表")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkList() {
        List<ChatInitialRet> list = chatDialogService.getChatDialogs();
        return WebResult.success(list);
    }

    @ApiOperation(value = "删除聊天窗口")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkDelete(@RequestBody ChatDelete chatDelete) {
        chatDialogService.deleteChatDialog(chatDelete.getListId());
        return WebResult.success();
    }

    @ApiOperation(value = "对话框置顶/取消置顶")
    @PostMapping(value = "/topping", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkTopping(@RequestBody ChatTop chatTop) {
        chatDialogService.setChatDialogTop(chatTop);
        return WebResult.success();
    }

    @ApiOperation(value = "设置/取消聊天消息免打扰")
    @PostMapping(value = "/disturb", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkDisturb(@RequestBody ChatDisturb chatDisturb) {
        chatDialogService.setChatDialogDisturb(chatDisturb);
        return WebResult.success();
    }

    @ApiOperation(value = "清空聊天窗口的未读消息")
    @PostMapping(value = "/unread/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkUnreadClear(@RequestBody ChatUnreadClear chatUnreadClear) {
        chatDialogService.clearUnread(chatUnreadClear.getReceiverId());
        return WebResult.success();
    }
}
