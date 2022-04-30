package cn.reghao.im.service;

import cn.reghao.im.db.mapper.*;
import cn.reghao.im.model.constant.MsgType;
import cn.reghao.im.model.dto.chat.ChatRecordGetRet;
import cn.reghao.im.model.dto.chat.ChatRecordGetRetList;
import cn.reghao.im.model.dto.message.CodeBlockResult;
import cn.reghao.im.model.dto.message.FileMsgResult;
import cn.reghao.im.model.po.message.CodeBlockMessage;
import cn.reghao.im.model.po.message.FileMessage;
import cn.reghao.im.model.po.message.TextMessage;
import cn.reghao.im.util.Jwt;
import cn.reghao.tnb.file.api.dto.FileInfoDto;
import cn.reghao.tnb.file.api.iface.FileInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-30 11:16:02
 */
@Service
public class ChatRecordService {
    @DubboReference(check = false)
    private FileInfoService fileInfoService;

    private final UserProfileMapper userProfileMapper;
    private final ChatDialogMapper chatDialogMapper;
    private final ChatRecordMapper chatRecordMapper;
    private final TextMessageMapper textMessageMapper;
    private final CodeBlockMessageMapper codeBlockMessageMapper;
    private final FileMessageMapper fileMessageMapper;

    public ChatRecordService(UserProfileMapper userProfileMapper, ChatDialogMapper chatDialogMapper,
                             ChatRecordMapper chatRecordMapper, TextMessageMapper textMessageMapper,
                             CodeBlockMessageMapper codeBlockMessageMapper, FileMessageMapper fileMessageMapper) {
        this.userProfileMapper = userProfileMapper;
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.textMessageMapper = textMessageMapper;
        this.codeBlockMessageMapper = codeBlockMessageMapper;
        this.fileMessageMapper = fileMessageMapper;
    }


    public ChatRecordGetRetList getChatRecords(long receiverId, long recordId, int limit) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ChatRecordGetRet> list = chatRecordMapper.findChatRecordGetRet(receiverId, userId, recordId, limit);
        list.forEach(chatRecordGetRet -> setRecordMessage(userId, chatRecordGetRet));

        ChatRecordGetRetList chatRecordGetRetList = new ChatRecordGetRetList();
        chatRecordGetRetList.setRows(list);
        if (list.isEmpty()) {
            chatRecordGetRetList.setRecordId(0);
        } else {
            chatRecordGetRetList.setRecordId(list.get(list.size()-1).getId());
        }
        chatRecordGetRetList.setLimit(limit);
        return chatRecordGetRetList;
    }

    private void setRecordMessage(long userId, ChatRecordGetRet chatRecordGetRet) {
        long recordId1 = chatRecordGetRet.getId();
        int msgType = chatRecordGetRet.getMsgType();
        boolean revoke = chatRecordGetRet.isRevoke();
        String createAt = chatRecordGetRet.getCreatedAt();
        if (revoke) {
            //
        } else if (msgType == MsgType.text.getCode()) {
            TextMessage textMessage = textMessageMapper.findByRecordId(recordId1);
            chatRecordGetRet.setContent(textMessage.getContent());
        } else if (msgType == MsgType.media.getCode()) {
            FileMessage fileMessage = fileMessageMapper.findByRecordId(recordId1);
            String uploadId = fileMessage.getUploadId();
            FileInfoDto fileInfoDto = fileInfoService.getFileInfo(uploadId);
            chatRecordGetRet.setFile(new FileMsgResult(fileMessage, fileInfoDto, userId, createAt));
        } else if (msgType == MsgType.chatRecord.getCode()) {

        } else if (msgType == MsgType.codeBlock.getCode()) {
            CodeBlockMessage codeBlockMessage = codeBlockMessageMapper.findByRecordId(recordId1);
            CodeBlockResult codeBlockResult = new CodeBlockResult(codeBlockMessage, userId);
            chatRecordGetRet.setCodeBlock(codeBlockResult);
        } else if (msgType == MsgType.vote.getCode()) {

        }
    }

    public ChatRecordGetRetList getChatRecords(int msgType, long receiverId, long recordId, int limit) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ChatRecordGetRet> list =
                chatRecordMapper.findChatRecordGetRetByMsgType(msgType, receiverId, userId, recordId, limit);
        list.forEach(chatRecordGetRet -> setRecordMessage(userId, chatRecordGetRet));

        ChatRecordGetRetList chatRecordGetRetList = new ChatRecordGetRetList();
        chatRecordGetRetList.setRows(list);
        if (list.isEmpty()) {
            chatRecordGetRetList.setRecordId(0);
        } else {
            chatRecordGetRetList.setRecordId(list.get(list.size()-1).getId());
        }
        chatRecordGetRetList.setLimit(limit);
        return chatRecordGetRetList;
    }
}
