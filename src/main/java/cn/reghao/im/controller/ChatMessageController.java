package cn.reghao.im.controller;

import cn.reghao.im.model.dto.chat.ChatRecordDelete;
import cn.reghao.im.model.dto.chat.ChatRecordRevoke;
import cn.reghao.im.model.dto.message.*;
import cn.reghao.im.model.dto.message.VoteMsgResult;
import cn.reghao.im.service.ChatMessageService;
import cn.reghao.im.util.WebResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-04-17 16:30:12
 */
@RestController
@RequestMapping("/api/v1/talk")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @ApiOperation(value = "发送文本消息")
    @PostMapping(value = "/message/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageText(@RequestBody TextMsg textMsg) throws IOException {
        chatMessageService.sendTextMessage(textMsg);
        return WebResult.success();
    }

    @ApiOperation(value = "发送表情包消息")
    @PostMapping(value = "/message/emoticon", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageEmoticon(@RequestBody EmoticonMsg emoticonMsg) {
        chatMessageService.sendEmoticonMessage(emoticonMsg);
        return WebResult.success();
    }

    @ApiOperation(value = "发送图片消息")
    @PostMapping(value = "/message/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageImage(ImageMsg imageMsg) throws IOException {
        chatMessageService.sendImageMessage(imageMsg);
        return WebResult.success();
    }

    @ApiOperation(value = "发送文件消息")
    @PostMapping(value = "/message/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageFile(@RequestBody FileMsg fileMsg) throws IOException {
        chatMessageService.sendFileMessage(fileMsg);
        return WebResult.success();
    }

    @ApiOperation(value = "转发聊天记录消息")
    @PostMapping(value = "/message/forward", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageForward(@RequestBody ForwardMsg forwardMsg) {
        return WebResult.success();
    }

    @ApiOperation(value = "发送代码块消息")
    @PostMapping(value = "/message/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageCode(@RequestBody CodeBlockMsg codeBlockMsg) throws IOException {
        chatMessageService.sendCodeBlockMessage(codeBlockMsg);
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

    @ApiOperation(value = "撤回消息")
    @PostMapping(value = "/message/revoke", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageRevoke(@RequestBody ChatRecordRevoke chatRecordRevoke) throws IOException {
        long recordId = chatRecordRevoke.getRecordId();
        chatMessageService.revokeChatRecord(recordId);
        return WebResult.success();
    }

    @ApiOperation(value = "删除消息")
    @PostMapping(value = "/message/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageDelete(@RequestBody ChatRecordDelete chatRecordDelete) {
        return WebResult.success();
    }

    @ApiOperation(value = "收藏(表情包)消息")
    @PostMapping(value = "/message/collect", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageCollect(@RequestParam("record_id") int recordId) {
        return WebResult.success();
    }
}
