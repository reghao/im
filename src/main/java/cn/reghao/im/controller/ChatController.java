package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.ChatDisturb;
import cn.reghao.im.model.dto.ChatInitial;
import cn.reghao.im.model.dto.ClearUnreadChat;
import cn.reghao.im.model.dto.GetChatRecord;
import cn.reghao.im.model.po.*;
import cn.reghao.im.model.vo.chat.ChatDialogVo;
import cn.reghao.im.model.vo.chat.ChatRecordVo;
import cn.reghao.im.model.vo.chat.ChatRecordList;
import cn.reghao.im.model.vo.message.CodeBlockResult;
import cn.reghao.im.model.vo.message.FileMsgResult;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.util.Jwt;
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
public class ChatController {
    @DubboReference(check = false)
    private FileInfoService fileInfoService;

    private final ChatMapper chatMapper;
    private final ChatDialogMapper chatDialogMapper;
    private final UserProfileMapper userProfileMapper;
    private final ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final FileMessageMapper fileMessageMapper;
    private final CodeMessageMapper codeMessageMapper;

    public ChatController(ChatMapper chatMapper, ChatDialogMapper chatDialogMapper, UserProfileMapper userProfileMapper,
                          ChatRecordMapper chatRecordMapper, TextMessageMapper textMessageMapper,
                          FileMessageMapper fileMessageMapper, CodeMessageMapper codeMessageMapper) {
        this.chatMapper = chatMapper;
        this.chatDialogMapper = chatDialogMapper;
        this.userProfileMapper = userProfileMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.textMessageMapper = textMessageMapper;
        this.fileMessageMapper = fileMessageMapper;
        this.codeMessageMapper = codeMessageMapper;
    }

    @ApiOperation(value = "创建聊天窗口")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkCreate(@RequestBody ChatInitial chatInitial) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = chatInitial.getReceiverId();
        int chatType = chatInitial.getTalkType();
        if (chatType == 1) {
            ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
            if (chatDialog == null) {
                Chat chat = new Chat(chatType);
                int chatId = chatMapper.save(chat);

                chatDialog = new ChatDialog(chatId, chatType, receiverId, userId);
                ChatDialog chatDialog1 = new ChatDialog(chatId, chatType, userId, receiverId);

                chatDialogMapper.save(chatDialog);
                chatDialogMapper.save(chatDialog1);
                chatDialog.setId(chatId);
            }

            UserInfo userInfo = userProfileMapper.findUserInfoByUserId(receiverId);
            ChatDialogVo chatDialogVo = new ChatDialogVo(chatDialog, userInfo.getNickname(), null, userInfo.getAvatar());
            return WebResult.success(chatDialogVo);
        } else if (chatType == 2) {

            return WebResult.success("群组对话框待实现");
        } else {
            return WebResult.failWithMsg("chatType 错误");
        }
    }

    @ApiOperation(value = "获取与联系人的聊天记录")
    @GetMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecords(@RequestParam("record_id") long recordId, @RequestParam("receiver_id") long receiverId,
                              @RequestParam("talk_type") int chatType, @RequestParam("limit") int limit) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        List<ChatRecord> chatRecords = chatRecordMapper.findByChatId(chatId, recordId, limit);
        List<ChatRecordVo> list = chatRecords.stream()
                .map(chatRecord -> {
                    UserInfo userInfo = userProfileMapper.findUserInfoByUserId(chatRecord.getSenderId());
                    ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, userInfo);
                    long recordId1 = chatRecord.getId();
                    int msgType = chatRecord.getMsgType();
                    boolean revoke = chatRecordVo.isRevoke();
                    if (revoke) {
                        //
                    } else if (msgType == MsgType.text.getCode()) {
                        TextMessage textMessage = textMessageMapper.findByRecordId(recordId1);
                        chatRecordVo.setContent(textMessage.getContent());
                    } else if (msgType == MsgType.media.getCode()) {
                        FileMessage fileMessage = fileMessageMapper.findByRecordId(recordId1);
                        long senderId = chatRecord.getSenderId();
                        String createAt = DateTimeConverter.format(chatRecord.getCreateAt());

                        String uploadId = fileMessage.getUploadId();
                        FileInfoDto fileInfoDto = fileInfoService.getFileInfo(uploadId);
                        chatRecordVo.setFile(new FileMsgResult(fileMessage, fileInfoDto, senderId, createAt));
                    } else if (msgType == MsgType.codeBlock.getCode()) {
                        // TODO 代码块消息
                        CodeMessage codeMessage = codeMessageMapper.findByRecordId(recordId1);
                        CodeBlockResult codeBlockResult = new CodeBlockResult(codeMessage, userId);
                        chatRecordVo.setCodeBlock(codeBlockResult);
                    }

                    return chatRecordVo;
                }).collect(Collectors.toList());

        ChatRecordList chatRecordList = new ChatRecordList();
        chatRecordList.setRows(list);
        if (list.isEmpty()) {
            chatRecordList.setRecordId(0);
        } else {
            chatRecordList.setRecordId(list.get(list.size()-1).getId());
        }
        chatRecordList.setLimit(limit);
        return WebResult.success(chatRecordList);
    }

    @ApiOperation(value = "清空未读消息")
    @PostMapping(value = "/unread/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkUnreadClear(@RequestBody ClearUnreadChat clearUnreadChat) {
        return WebResult.success();
    }

    @ApiOperation(value = "删除聊天窗口")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkDelete(@RequestParam("list_id") int listId) {
        return WebResult.success();
    }

    @ApiOperation(value = "获取聊天窗口列表")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkList() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ChatDialog> list = chatDialogMapper.findByUserId(userId);

        List<ChatDialogVo> list1 = list.stream().map(chatDialog -> {
            long receiverId = chatDialog.getReceiverId();
            UserInfo userInfo = userProfileMapper.findUserInfoByUserId(receiverId);
            return new ChatDialogVo(chatDialog, userInfo.getNickname(), "", userInfo.getAvatar());
        }).collect(Collectors.toList());
        return WebResult.success(list1);
    }

    @ApiOperation(value = "窗口列表置顶/取消置顶")
    @PostMapping(value = "/topping", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkTopping(@RequestParam("list_id") int listId, @RequestParam("type") int type) {
        // type = 1 -> 置顶, type = 2 -> 取消置顶
        return WebResult.success();
    }

    @ApiOperation(value = "设置/取消聊天消息免打扰")
    @PostMapping(value = "/disturb", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkDisturb(@RequestBody ChatDisturb chatDisturb) {
        return WebResult.success();
    }

    @ApiOperation(value = "获取与联系人的历史聊天记录")
    @GetMapping(value = "/records/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecordsHistory(GetChatRecord getChatRecord) {
        ChatRecordList chatRecordList = new ChatRecordList();
        List<ChatRecordVo> list = new ArrayList<>();
        chatRecordList.setRows(list);
        chatRecordList.setRecordId(1);
        chatRecordList.setLimit(100);
        return WebResult.success(chatRecordList);
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
