package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.ChatDialogMapper;
import cn.reghao.im.db.mapper.TextMessageMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.dto.ChatDisturb;
import cn.reghao.im.model.dto.ChatInitial;
import cn.reghao.im.model.dto.ClearUnreadChat;
import cn.reghao.im.model.dto.GetChatRecord;
import cn.reghao.im.model.po.ChatDialog;
import cn.reghao.im.model.po.TextMessage;
import cn.reghao.im.model.vo.chat.ChatDialogVo;
import cn.reghao.im.model.vo.chat.ChatRecord;
import cn.reghao.im.model.vo.chat.ChatRecordList;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.util.Jwt;
import io.swagger.annotations.ApiOperation;
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
    private final ChatDialogMapper chatDialogMapper;
    private final UserProfileMapper userProfileMapper;
    private TextMessageMapper textMessageMapper;

    public ChatController(ChatDialogMapper chatDialogMapper, UserProfileMapper userProfileMapper,
                          TextMessageMapper textMessageMapper) {
        this.chatDialogMapper = chatDialogMapper;
        this.userProfileMapper = userProfileMapper;
        this.textMessageMapper = textMessageMapper;
    }

    @ApiOperation(value = "创建聊天窗口")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkCreate(@RequestBody ChatInitial chatInitial) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = chatInitial.getReceiverId();
        int talkType = chatInitial.getTalkType();
        ChatDialog chatDialog = chatDialogMapper.findBySenderAndReceiverId(userId, receiverId);
        if (chatDialog == null) {
            chatDialog = new ChatDialog(talkType, userId, receiverId);
            int chatId = chatDialogMapper.save(chatDialog);
            chatDialog.setId(chatId);
        }

        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(receiverId);
        ChatDialogVo chatDialogVo = new ChatDialogVo(chatDialog, userInfo.getNickname(), null, userInfo.getAvatar());
        return WebResult.success(chatDialogVo);
    }

    @ApiOperation(value = "获取与联系人的聊天记录")
    @GetMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkRecords(@RequestParam("record_id") long recordId, @RequestParam("receiver_id") long receiverId,
                              @RequestParam("talk_type") int talkType, @RequestParam("limit") int limit) {
        long senderId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<TextMessage> textMessages = textMessageMapper.findBySenderAndReceiverId(senderId, receiverId);

        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(senderId);
        String nickname = userInfo.getNickname();
        String avatar = userInfo.getAvatar();
        List<ChatRecord> list = textMessages.stream().map(textMessage -> new ChatRecord(textMessage, nickname, avatar))
                .collect(Collectors.toList());
        ChatRecordList chatRecordList = new ChatRecordList();
        chatRecordList.setRows(list);
        chatRecordList.setRecordId(1);
        chatRecordList.setLimit(textMessages.size());
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
        List<ChatDialog> list = chatDialogMapper.findBySenderId(userId);
        List<ChatDialogVo> list1 = list.stream().map(chatDialog -> {
            long receiverId = chatDialog.getReceiverId();
            UserInfo userInfo = userProfileMapper.findUserInfoByUserId(receiverId);
            return new ChatDialogVo(chatDialog, userInfo.getNickname(), null, userInfo.getAvatar());
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
        List<ChatRecord> list = new ArrayList<>();
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
