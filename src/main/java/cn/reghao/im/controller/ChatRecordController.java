package cn.reghao.im.controller;

import cn.reghao.im.model.dto.chat.*;
import cn.reghao.im.service.ChatRecordService;
import cn.reghao.im.util.WebResult;
import cn.reghao.tnb.file.api.iface.FileInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/talk")
public class ChatRecordController {
    @DubboReference(check = false)
    private FileInfoService fileInfoService;
    private final ChatRecordService chatRecordService;

    public ChatRecordController(ChatRecordService chatRecordService) {
        this.chatRecordService = chatRecordService;
    }

    @ApiOperation(value = "获取与联系人的聊天记录")
    @GetMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecords(ChatRecordGet chatRecordGet) {
        long receiverId = chatRecordGet.getReceiver_id();
        long recordId = chatRecordGet.getRecord_id();
        int limit = chatRecordGet.getLimit();
        int chatType = chatRecordGet.getTalk_type();
        ChatRecordGetRetList chatRecordGetRetList = chatRecordService.getChatRecords(receiverId, recordId, limit);
        return WebResult.success(chatRecordGetRetList);
    }

    @ApiOperation(value = "获取与联系人的历史聊天记录")
    @GetMapping(value = "/records/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecordsHistory(ChatRecordGet chatRecordGet) {
        long receiverId = chatRecordGet.getReceiver_id();
        long recordId = chatRecordGet.getRecord_id();
        int limit = chatRecordGet.getLimit();
        int msgType = chatRecordGet.getMsg_type();

        ChatRecordGetRetList list;
        if (msgType == 0) {
            list = chatRecordService.getChatRecords(receiverId, recordId, limit);
        } else {
            list = chatRecordService.getChatRecords(msgType, receiverId, recordId, limit);
        }
        return WebResult.success(list);
    }

    @ApiOperation(value = "转发聊天记录")
    @GetMapping(value = "/records/forward", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecordsForward() {
        return WebResult.success();
    }

    @GetMapping(value = "/search-chat-records", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkSearchChatRecords() {
        return WebResult.success();
    }

    @GetMapping(value = "/get-records-context", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkGetRecordsContext() {
        return WebResult.success();
    }
}
