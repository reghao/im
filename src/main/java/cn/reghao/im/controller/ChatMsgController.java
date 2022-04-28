package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.RevokeRecord;
import cn.reghao.im.model.dto.message.*;
import cn.reghao.im.model.po.*;
import cn.reghao.im.model.vo.chat.ChatRecordVo;
import cn.reghao.im.model.vo.message.CodeBlockResult;
import cn.reghao.im.model.vo.message.FileMsgResult;
import cn.reghao.im.model.vo.message.VoteMsgResult;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.model.ws.resp.EventMessageResp;
import cn.reghao.im.model.ws.resp.EvtTalkResp;
import cn.reghao.im.model.ws.resp.EvtTalkRevokeResp;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.ws.EventDispatcher;
import cn.reghao.im.model.constant.EventType;
import cn.reghao.im.util.Jwt;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.tnb.file.api.dto.FileInfoDto;
import cn.reghao.tnb.file.api.iface.FileInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-04-17 16:30:12
 */
@RestController
@RequestMapping("/api/v1/talk")
public class ChatMsgController {
    @DubboReference(check = false)
    private FileInfoService fileInfoService;

    private final EventDispatcher eventDispatcher;
    private final UserProfileMapper userProfileMapper;
    private final ChatDialogMapper chatDialogMapper;
    private final ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final CodeMessageMapper codeMessageMapper;
    private final FileMessageMapper fileMessageMapper;

    public ChatMsgController(EventDispatcher eventDispatcher, UserProfileMapper userProfileMapper,
                             ChatDialogMapper chatDialogMapper, ChatRecordMapper chatRecordMapper,
                             TextMessageMapper textMessageMapper, CodeMessageMapper codeMessageMapper,
                             FileMessageMapper fileMessageMapper) {
        this.eventDispatcher = eventDispatcher;
        this.userProfileMapper = userProfileMapper;
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.textMessageMapper = textMessageMapper;
        this.codeMessageMapper = codeMessageMapper;
        this.fileMessageMapper = fileMessageMapper;
    }

    @ApiOperation(value = "发送文本消息")
    @PostMapping(value = "/message/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageText(@RequestBody TextMsg textMsg) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = textMsg.getReceiverId();
        int chatType = textMsg.getTalkType();

        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();

        ChatRecord chatRecord = new ChatRecord(chatId, chatType, userId, receiverId, MsgType.text.getCode());
        chatRecordMapper.save(chatRecord);

        int recordId = chatRecord.getId();
        String text = textMsg.getText();
        TextMessage textMessage = new TextMessage(recordId, text);
        textMessageMapper.save(textMessage);

        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, userInfo);
        chatRecordVo.setContent(textMessage.getContent());

        EvtTalkResp<ChatRecordVo> resp = new EvtTalkResp<>(chatType, userId, receiverId, chatRecordVo);
        EventMessageResp<EvtTalkResp<ChatRecordVo>> eventMessage = new EventMessageResp<>(EventType.event_talk, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(userId, eventMessage);
        return WebResult.success();
    }

    @ApiOperation(value = "发送代码块消息")
    @PostMapping(value = "/message/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageCode(@RequestBody CodeBlockMsg codeBlockMsg) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = codeBlockMsg.getReceiverId();
        int chatType = codeBlockMsg.getTalkType();

        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();

        ChatRecord chatRecord = new ChatRecord(chatId, chatType, userId, receiverId, MsgType.codeBlock.getCode());
        chatRecordMapper.save(chatRecord);

        int recordId = chatRecord.getId();
        String lang = codeBlockMsg.getLang();
        String code = codeBlockMsg.getCode();
        CodeMessage codeMessage = new CodeMessage(recordId, lang, code);
        codeMessageMapper.save(codeMessage);

        CodeBlockResult codeBlockResult = new CodeBlockResult(codeMessage, userId);
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, userInfo);
        chatRecordVo.setCodeBlock(codeBlockResult);

        EvtTalkResp<ChatRecordVo> resp = new EvtTalkResp<>(chatType, userId, receiverId, chatRecordVo);
        EventMessageResp<EvtTalkResp<ChatRecordVo>> eventMessage = new EventMessageResp<>(EventType.event_talk, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(userId, eventMessage);
        return WebResult.success();
    }

    @ApiOperation(value = "发送文件消息")
    @PostMapping(value = "/message/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageFile(@RequestBody FileMsg fileMsg) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = fileMsg.getReceiverId();
        int chatType = fileMsg.getTalkType();
        String uploadId = fileMsg.getUploadId();

        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        ChatRecord chatRecord = new ChatRecord(chatId, chatType, userId, receiverId, MsgType.media.getCode());
        chatRecordMapper.save(chatRecord);

        int recordId = chatRecord.getId();
        FileMessage fileMessage = new FileMessage(recordId, uploadId);
        fileMessageMapper.save(fileMessage);

        FileInfoDto fileInfoDto = fileInfoService.getFileInfo(uploadId);
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, userInfo);
        String createAt = DateTimeConverter.format(chatRecord.getCreateAt());
        chatRecordVo.setFile(new FileMsgResult(fileMessage, fileInfoDto, userId, createAt));

        EvtTalkResp<ChatRecordVo> resp = new EvtTalkResp<>(chatType, userId, receiverId, chatRecordVo);
        EventMessageResp<EvtTalkResp<ChatRecordVo>> eventMessage = new EventMessageResp<>(EventType.event_talk, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(userId, eventMessage);
        return WebResult.success();
    }

    @ApiOperation(value = "发送图片消息")
    @PostMapping(value = "/message/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageImage(@RequestParam("talk_type") int chatType,
                                   @RequestParam("receiver_id") long receiverId,
                                   @RequestParam("upload_id") String uploadId) throws IOException {

        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        ChatRecord chatRecord = new ChatRecord(chatId, chatType, userId, receiverId, MsgType.media.getCode());
        chatRecordMapper.save(chatRecord);

        int recordId = chatRecord.getId();
        FileMessage fileMessage = new FileMessage(recordId, uploadId);
        fileMessageMapper.save(fileMessage);

        FileInfoDto fileInfoDto = fileInfoService.getFileInfo(uploadId);
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        ChatRecordVo chatRecordVo = new ChatRecordVo(chatRecord, userInfo);
        String createAt = DateTimeConverter.format(chatRecord.getCreateAt());
        chatRecordVo.setFile(new FileMsgResult(fileMessage, fileInfoDto, userId, createAt));

        EvtTalkResp<ChatRecordVo> resp = new EvtTalkResp<>(chatType, userId, receiverId, chatRecordVo);
        EventMessageResp<EvtTalkResp<ChatRecordVo>> eventMessage = new EventMessageResp<>(EventType.event_talk, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(userId, eventMessage);
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
    public String talkMessageRevoke(@RequestBody RevokeRecord revokeRecord) throws IOException {
        long recordId = revokeRecord.getRecordId();
        chatRecordMapper.updateSetRevoke(recordId);
        ChatRecord chatRecord = chatRecordMapper.findByRecordId(revokeRecord.getRecordId());
        int chatType = chatRecord.getChatType();
        long senderId = chatRecord.getSenderId();
        long receiverId = chatRecord.getReceiverId();

        EvtTalkRevokeResp resp = new EvtTalkRevokeResp(chatType, senderId, receiverId, revokeRecord.getRecordId());
        EventMessageResp<EvtTalkRevokeResp> eventMessage = new EventMessageResp<>(EventType.event_talk_revoke, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(senderId, eventMessage);
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
