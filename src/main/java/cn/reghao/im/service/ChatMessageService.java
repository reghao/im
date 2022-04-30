package cn.reghao.im.service;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.chat.ChatRecordGetRet;
import cn.reghao.im.model.dto.message.*;
import cn.reghao.im.model.dto.user.UserInfo;
import cn.reghao.im.model.po.chat.ChatDialog;
import cn.reghao.im.model.po.chat.ChatRecord;
import cn.reghao.im.model.po.message.CodeBlockMessage;
import cn.reghao.im.model.po.message.FileMessage;
import cn.reghao.im.model.po.message.TextMessage;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.ws.EventDispatcher;
import cn.reghao.im.ws.model.EventType;
import cn.reghao.im.ws.model.resp.EventMessageResp;
import cn.reghao.im.ws.model.resp.EvtTalkResp;
import cn.reghao.im.ws.model.resp.EvtTalkRevokeResp;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;
import cn.reghao.tnb.file.api.dto.FileInfoDto;
import cn.reghao.tnb.file.api.iface.FileInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-04-30 15:08:12
 */
@Service
public class ChatMessageService {
    @DubboReference(check = false)
    private FileInfoService fileInfoService;

    private final EventDispatcher eventDispatcher;
    private final UserProfileMapper userProfileMapper;
    private final ChatDialogMapper chatDialogMapper;
    private final ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final CodeBlockMessageMapper codeBlockMessageMapper;
    private final FileMessageMapper fileMessageMapper;

    public ChatMessageService(EventDispatcher eventDispatcher, UserProfileMapper userProfileMapper,
                                 ChatDialogMapper chatDialogMapper, ChatRecordMapper chatRecordMapper,
                                 TextMessageMapper textMessageMapper, CodeBlockMessageMapper codeBlockMessageMapper,
                                 FileMessageMapper fileMessageMapper) {
        this.eventDispatcher = eventDispatcher;
        this.userProfileMapper = userProfileMapper;
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.textMessageMapper = textMessageMapper;
        this.codeBlockMessageMapper = codeBlockMessageMapper;
        this.fileMessageMapper = fileMessageMapper;
    }

    public void sendTextMessage(TextMsg textMsg) throws IOException {
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
        ChatRecordGetRet chatRecordGetRet = new ChatRecordGetRet(chatRecord, userInfo);
        chatRecordGetRet.setContent(textMessage.getContent());

        sendMessageEvent(chatType, userId, receiverId, chatRecordGetRet);
    }

    public void sendEmoticonMessage(EmoticonMsg emoticonMsg) {
    }

    public void sendImageMessage(ImageMsg imageMsg) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = imageMsg.getReceiver_id();
        int chatType = imageMsg.getTalk_type();
        String uploadId = imageMsg.getUpload_id();

        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        long chatId = chatDialog.getChatId();
        ChatRecord chatRecord = new ChatRecord(chatId, chatType, userId, receiverId, MsgType.media.getCode());
        chatRecordMapper.save(chatRecord);

        int recordId = chatRecord.getId();
        FileMessage fileMessage = new FileMessage(recordId, uploadId);
        fileMessageMapper.save(fileMessage);

        FileInfoDto fileInfoDto = fileInfoService.getFileInfo(uploadId);
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        ChatRecordGetRet chatRecordGetRet = new ChatRecordGetRet(chatRecord, userInfo);
        String createAt = DateTimeConverter.format(chatRecord.getCreateTime());
        chatRecordGetRet.setFile(new FileMsgResult(fileMessage, fileInfoDto, userId, createAt));

        sendMessageEvent(chatType, userId, receiverId, chatRecordGetRet);
    }

    public void sendFileMessage(FileMsg fileMsg) throws IOException {
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
        ChatRecordGetRet chatRecordGetRet = new ChatRecordGetRet(chatRecord, userInfo);
        String createAt = DateTimeConverter.format(chatRecord.getCreateTime());
        chatRecordGetRet.setFile(new FileMsgResult(fileMessage, fileInfoDto, userId, createAt));

        sendMessageEvent(chatType, userId, receiverId, chatRecordGetRet);
    }

    public void sendChatRecordMessage(ForwardMsg forwardMsg) {
    }

    public void sendCodeBlockMessage(CodeBlockMsg codeBlockMsg) throws IOException {
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
        CodeBlockMessage codeBlockMessage = new CodeBlockMessage(recordId, lang, code);
        codeBlockMessageMapper.save(codeBlockMessage);

        CodeBlockResult codeBlockResult = new CodeBlockResult(codeBlockMessage, userId);
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        ChatRecordGetRet chatRecordGetRet = new ChatRecordGetRet(chatRecord, userInfo);
        chatRecordGetRet.setCodeBlock(codeBlockResult);

        sendMessageEvent(chatType, userId, receiverId, chatRecordGetRet);
    }

    public void sendVoteMessage(VoteMsg voteMsg) {
    }

    private void sendMessageEvent(int chatType, long userId, long receiverId, ChatRecordGetRet chatRecordGetRet)
            throws IOException {
        EvtTalkResp<ChatRecordGetRet> resp = new EvtTalkResp<>(chatType, userId, receiverId, chatRecordGetRet);
        EventMessageResp<EvtTalkResp<ChatRecordGetRet>> eventMessage = new EventMessageResp<>(EventType.event_talk, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(userId, eventMessage);
    }

    public void revokeChatRecord(long recordId) throws IOException {
        chatRecordMapper.updateSetRevoke(recordId);

        ChatRecord chatRecord = chatRecordMapper.findByRecordId(recordId);
        int chatType = chatRecord.getChatType();
        long senderId = chatRecord.getSenderId();
        long receiverId = chatRecord.getReceiverId();

        EvtTalkRevokeResp resp = new EvtTalkRevokeResp(chatType, senderId, receiverId, recordId);
        EventMessageResp<EvtTalkRevokeResp> eventMessage = new EventMessageResp<>(EventType.event_talk_revoke, resp);
        eventDispatcher.sendMessage(receiverId, eventMessage);
        eventDispatcher.sendMessage(senderId, eventMessage);
    }

    public void deleteChatRecord(long recordId) {

    }
}
