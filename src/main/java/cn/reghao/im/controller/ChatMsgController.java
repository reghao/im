package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.ChatDialogMapper;
import cn.reghao.im.db.mapper.ChatRecordMapper;
import cn.reghao.im.db.mapper.TextMessageMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.constant.FileMsgType;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.message.*;
import cn.reghao.im.model.po.ChatDialog;
import cn.reghao.im.model.po.ChatRecord;
import cn.reghao.im.model.po.FileMessage;
import cn.reghao.im.model.po.TextMessage;
import cn.reghao.im.model.vo.chat.ChatRecordVo;
import cn.reghao.im.model.vo.message.FileMsgResult;
import cn.reghao.im.model.vo.message.VoteMsgResult;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.model.ws.*;
import cn.reghao.im.service.FileService;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.ws.WebSocketHandlerImpl;
import cn.reghao.im.model.constant.EventType;
import cn.reghao.im.util.Jwt;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-04-17 16:30:12
 */
@RestController
@RequestMapping("/api/v1/talk")
public class ChatMsgController {
    private final WebSocketHandlerImpl webSocketHandler;
    private final UserProfileMapper userProfileMapper;
    private final ChatDialogMapper chatDialogMapper;
    private ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final FileService fileService;

    public ChatMsgController(WebSocketHandlerImpl webSocketHandler, UserProfileMapper userProfileMapper,
                             ChatDialogMapper chatDialogMapper, ChatRecordMapper chatRecordMapper,
                             TextMessageMapper textMessageMapper, FileService fileService) {
        this.webSocketHandler = webSocketHandler;
        this.userProfileMapper = userProfileMapper;
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.textMessageMapper = textMessageMapper;
        this.fileService = fileService;
    }

    @ApiOperation(value = "发送文本消息")
    @PostMapping(value = "/message/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageText(@RequestBody TextMsg textMsg) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = textMsg.getReceiverId();
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        ChatRecord chatRecord = new ChatRecord(chatId, userId, MsgType.text.getCode());
        chatRecordMapper.save(chatRecord);
        int recordId = chatRecord.getId();

        int chatType = textMsg.getTalkType();
        String text = textMsg.getText();
        TextMessage textMessage = new TextMessage(recordId, text);
        textMessageMapper.save(textMessage);

        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        String nickname = userInfo.getNickname();
        String avatar = userInfo.getAvatar();
        ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, nickname, avatar, chatType, receiverId);
        chatRecordVo.setContent(textMessage.getContent());

        EventContent<ChatRecordVo> eventContent = new EventContent<>(chatType, userId, receiverId, chatRecordVo);
        EventMessage<ChatRecordVo> eventMessage = new EventMessage<>(EventType.event_talk, eventContent);
        webSocketHandler.sendMessage(receiverId, eventMessage);
        webSocketHandler.sendMessage(userId, eventMessage);
        return WebResult.success();
    }

    @ApiOperation(value = "发送代码块消息")
    @PostMapping(value = "/message/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageCode(@RequestBody CodeBlockMsg codeBlockMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "发送文件消息")
    @PostMapping(value = "/message/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageFile(@RequestBody FileMsg fileMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "发送图片消息")
    @PostMapping(value = "/message/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageImage(@RequestParam("talk_type") int chatType, @RequestParam("receiver_id") long receiverId,
                                   @RequestParam("image") MultipartFile image) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        ChatRecord chatRecord = new ChatRecord(chatId, userId, MsgType.media.getCode());
        chatRecordMapper.save(chatRecord);
        int recordId = chatRecord.getId();
        FileMessage fileMessage = fileService.saveFile(image, recordId, FileMsgType.image.getCode());

        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        String nickname = userInfo.getNickname();
        String avatar = userInfo.getAvatar();
        ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, nickname, avatar, chatType, receiverId);
        String createAt = DateTimeConverter.format(chatRecord.getCreateAt());
        chatRecordVo.setFile(new FileMsgResult(fileMessage, userId, createAt));

        EventContent<ChatRecordVo> eventContent = new EventContent<>(chatType, userId, receiverId, chatRecordVo);
        EventMessage<ChatRecordVo> eventMessage = new EventMessage<>(EventType.event_talk, eventContent);
        webSocketHandler.sendMessage(receiverId, eventMessage);
        webSocketHandler.sendMessage(userId, eventMessage);
        return WebResult.success();
    }

    @ApiOperation(value = "发送表情包消息")
    @PostMapping(value = "/message/emoticon", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageEmoticon(@RequestBody EmoticonMsg emoticonMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "转发消息")
    @PostMapping(value = "/message/forward", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageForward(@RequestBody ForwardMsg forwardMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "撤回消息")
    @PostMapping(value = "/message/revoke", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageRevoke(@RequestParam("record_id") int recordId) {
        return WebResult.success();
    }

    @ApiOperation(value = "删除消息")
    @PostMapping(value = "/message/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageDelete(@RequestBody DeleteMsg deleteMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "收藏(表情包)消息")
    @PostMapping(value = "/message/collect", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageCollect(@RequestParam("record_id") int recordId) {
        return WebResult.success();
    }

    @ApiOperation(value = "(群组中)发送投票消息")
    @PostMapping(value = "/message/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageVote(@RequestBody VoteMsg voteMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "(群组中)进行投票")
    @PostMapping(value = "/message/vote/handle", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageVoteHandle(@RequestParam("record_id") int recordId,
                                        @RequestParam("options") String options) {
        VoteMsgResult voteMsgResult = new VoteMsgResult();
        return WebResult.success(voteMsgResult);
    }
}
