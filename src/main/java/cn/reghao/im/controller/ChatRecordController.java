package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.chat.*;
import cn.reghao.im.model.dto.message.CodeBlockResult;
import cn.reghao.im.model.dto.message.FileMsgResult;
import cn.reghao.im.model.dto.user.UserInfo;
import cn.reghao.im.model.po.chat.ChatDialog;
import cn.reghao.im.model.po.chat.ChatRecord;
import cn.reghao.im.model.po.message.CodeMessage;
import cn.reghao.im.model.po.message.FileMessage;
import cn.reghao.im.model.po.message.TextMessage;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.util.WebResult;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.tnb.file.api.dto.FileInfoDto;
import cn.reghao.tnb.file.api.iface.FileInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/talk")
public class ChatRecordController {
    @DubboReference(check = false)
    private FileInfoService fileInfoService;

    private final ChatDialogMapper chatDialogMapper;
    private final UserProfileMapper userProfileMapper;
    private final ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final FileMessageMapper fileMessageMapper;
    private final CodeMessageMapper codeMessageMapper;

    public ChatRecordController(ChatDialogMapper chatDialogMapper, UserProfileMapper userProfileMapper,
                                ChatRecordMapper chatRecordMapper, TextMessageMapper textMessageMapper,
                                FileMessageMapper fileMessageMapper, CodeMessageMapper codeMessageMapper) {
        this.chatDialogMapper = chatDialogMapper;
        this.userProfileMapper = userProfileMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.textMessageMapper = textMessageMapper;
        this.fileMessageMapper = fileMessageMapper;
        this.codeMessageMapper = codeMessageMapper;
    }

    @ApiOperation(value = "获取与联系人的聊天记录")
    @GetMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecords(@RequestParam("record_id") long recordId, @RequestParam("receiver_id") long receiverId,
                              @RequestParam("talk_type") int chatType, @RequestParam("limit") int limit) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        List<ChatRecord> chatRecords = chatRecordMapper.findByChatId(chatId, recordId, limit);
        List<ChatRecordGetRet> list = chatRecords.stream()
                .map(chatRecord -> {
                    UserInfo userInfo = userProfileMapper.findUserInfoByUserId(chatRecord.getSenderId());
                    ChatRecordGetRet chatRecordGetRet = new ChatRecordGetRet(chatRecord, userInfo);
                    long recordId1 = chatRecord.getId();
                    int msgType = chatRecord.getMsgType();
                    boolean revoke = chatRecordGetRet.isRevoke();
                    if (revoke) {
                        //
                    } else if (msgType == MsgType.text.getCode()) {
                        TextMessage textMessage = textMessageMapper.findByRecordId(recordId1);
                        chatRecordGetRet.setContent(textMessage.getContent());
                    } else if (msgType == MsgType.media.getCode()) {
                        FileMessage fileMessage = fileMessageMapper.findByRecordId(recordId1);
                        long senderId = chatRecord.getSenderId();
                        String createAt = DateTimeConverter.format(chatRecord.getCreateAt());

                        String uploadId = fileMessage.getUploadId();
                        FileInfoDto fileInfoDto = fileInfoService.getFileInfo(uploadId);
                        chatRecordGetRet.setFile(new FileMsgResult(fileMessage, fileInfoDto, senderId, createAt));
                    } else if (msgType == MsgType.codeBlock.getCode()) {
                        // TODO 代码块消息
                        CodeMessage codeMessage = codeMessageMapper.findByRecordId(recordId1);
                        CodeBlockResult codeBlockResult = new CodeBlockResult(codeMessage, userId);
                        chatRecordGetRet.setCodeBlock(codeBlockResult);
                    }

                    return chatRecordGetRet;
                }).collect(Collectors.toList());

        ChatRecordGetRetList chatRecordGetRetList = new ChatRecordGetRetList();
        chatRecordGetRetList.setRows(list);
        if (list.isEmpty()) {
            chatRecordGetRetList.setRecordId(0);
        } else {
            chatRecordGetRetList.setRecordId(list.get(list.size()-1).getId());
        }
        chatRecordGetRetList.setLimit(limit);
        return WebResult.success(chatRecordGetRetList);
    }

    @ApiOperation(value = "获取与联系人的历史聊天记录")
    @GetMapping(value = "/records/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecordsHistory(ChatRecordGet chatRecordGet) {
        ChatRecordGetRetList chatRecordGetRetList = new ChatRecordGetRetList();
        List<ChatRecordGetRet> list = new ArrayList<>();
        chatRecordGetRetList.setRows(list);
        chatRecordGetRetList.setRecordId(1);
        chatRecordGetRetList.setLimit(100);
        return WebResult.success(chatRecordGetRetList);
    }

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
