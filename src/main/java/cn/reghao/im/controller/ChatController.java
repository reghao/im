package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.chat.*;
import cn.reghao.im.model.dto.message.CodeBlockResult;
import cn.reghao.im.model.dto.message.FileMsgResult;
import cn.reghao.im.model.dto.user.UserInfo;
import cn.reghao.im.model.po.chat.Chat;
import cn.reghao.im.model.po.chat.ChatDialog;
import cn.reghao.im.model.po.chat.ChatRecord;
import cn.reghao.im.model.po.contact.ChatGroup;
import cn.reghao.im.model.po.message.CodeMessage;
import cn.reghao.im.model.po.message.FileMessage;
import cn.reghao.im.model.po.message.TextMessage;
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
    private final ChatGroupMapper chatGroupMapper;
    private final ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final FileMessageMapper fileMessageMapper;
    private final CodeMessageMapper codeMessageMapper;

    public ChatController(ChatMapper chatMapper, ChatDialogMapper chatDialogMapper, UserProfileMapper userProfileMapper,
                          ChatGroupMapper chatGroupMapper,
                          ChatRecordMapper chatRecordMapper, TextMessageMapper textMessageMapper,
                          FileMessageMapper fileMessageMapper, CodeMessageMapper codeMessageMapper) {
        this.chatMapper = chatMapper;
        this.chatDialogMapper = chatDialogMapper;
        this.chatGroupMapper = chatGroupMapper;
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
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        if (chatDialog == null) {
            Chat chat = new Chat(chatType);
            chatMapper.save(chat);

            int chatId = chat.getId();
            List<ChatDialog> list = new ArrayList<>();
            if (chatType == 1) {
                list.add(new ChatDialog(chatId, chatType, receiverId, userId));
                list.add(new ChatDialog(chatId, chatType, userId, receiverId));
                chatDialogMapper.saveAll(list);
            } else {
                List<Long> userIds = chatGroupMapper.findUserIdsByGroupId(receiverId);
                list = userIds.stream()
                        .map(memberId -> new ChatDialog(chatId, chatType, receiverId, memberId))
                        .collect(Collectors.toList());
                chatDialogMapper.saveAll(list);
            }
            chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        }

        String name;
        String avatar;
        if (chatType == 1) {
            UserInfo userInfo = userProfileMapper.findUserInfoByUserId(receiverId);
            name = userInfo.getNickname();
            avatar = userInfo.getAvatar();
        } else if (chatType == 2) {
            ChatGroup chatGroup = chatGroupMapper.findByGroupId(receiverId);
            name = chatGroup.getName();
            avatar = chatGroup.getAvatar();
        } else {
            return WebResult.failWithMsg("chatType 错误");
        }

        ChatDialogVo chatDialogVo = new ChatDialogVo(chatDialog, name, null, avatar);
        return WebResult.success(chatDialogVo);
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
            int chatType = chatDialog.getChatType();
            long receiverId = chatDialog.getReceiverId();
            String name;
            String avatar;
            if (chatType == 1) {
                UserInfo userInfo = userProfileMapper.findUserInfoByUserId(receiverId);
                name = userInfo.getNickname();
                avatar = userInfo.getAvatar();
            } else {
                ChatGroup chatGroup = chatGroupMapper.findByGroupId(receiverId);
                name = chatGroup.getName();
                avatar = chatGroup.getAvatar();
            }

            return new ChatDialogVo(chatDialog, name, "", avatar);
        }).collect(Collectors.toList());
        return WebResult.success(list1);
    }

    @ApiOperation(value = "对话框置顶/取消置顶")
    @PostMapping(value = "/topping", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkTopping(@RequestBody DialogTopping dialogTopping) {
        // type = 1 -> 置顶, type = 2 -> 取消置顶
        int dialogId = dialogTopping.getListId();
        int type = dialogTopping.getType();
        if (type == 1) {
            chatDialogMapper.updateSetTop(dialogId, true);
        } else if (type == 2) {
            chatDialogMapper.updateSetTop(dialogId, false);
        }
        return WebResult.success();
    }

    @ApiOperation(value = "设置/取消聊天消息免打扰")
    @PostMapping(value = "/disturb", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkDisturb(@RequestBody ChatDisturb chatDisturb) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        int chatType = chatDisturb.getTalkType();
        long receiverId = chatDisturb.getReceiverId();
        ChatDialog chatDialog = chatDialogMapper.findChatDialog(chatType, receiverId, userId);
        chatDialog.setDisturb(chatDisturb.isDisturb());
        chatDialogMapper.updateSetDisturb(chatDialog);
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
