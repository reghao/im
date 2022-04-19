package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.TextMessageMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.dto.message.*;
import cn.reghao.im.model.po.TextMessage;
import cn.reghao.im.model.vo.message.VoteMsgResult;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.model.ws.TalkEvtMsg;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.ws.WebSocketHandlerImpl;
import cn.reghao.im.ws.msg.ImEvent;
import cn.reghao.im.ws.msg.Talk;
import cn.reghao.im.ws.msg.WsMsg;
import cn.reghao.im.util.Jwt;
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
public class ChatMsgController {
    private final WebSocketHandlerImpl webSocketHandler;
    private final UserProfileMapper userProfileMapper;
    private TextMessageMapper textMessageMapper;

    public ChatMsgController(WebSocketHandlerImpl webSocketHandler, UserProfileMapper userProfileMapper,
                             TextMessageMapper textMessageMapper) {
        this.webSocketHandler = webSocketHandler;
        this.userProfileMapper = userProfileMapper;
        this.textMessageMapper = textMessageMapper;
    }

    @ApiOperation(value = "发送文本消息")
    @PostMapping(value = "/message/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public String talkMessageText(@RequestBody TextMsg textMsg) throws IOException {
        long senderId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = textMsg.getReceiverId();
        int talkType = textMsg.getTalkType();
        String text = textMsg.getText();

        TextMessage textMessage = new TextMessage(talkType, senderId, receiverId, text);
        textMessageMapper.save(textMessage);

        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(senderId);
        TalkEvtMsg talkEvtMsg = new TalkEvtMsg(textMessage, userInfo.getNickname(), userInfo.getAvatar());
        Talk talk = new Talk(senderId, receiverId, talkType);
        talk.setData(talkEvtMsg);

        WsMsg<Talk> wsMsg = new WsMsg<>(ImEvent.event_talk, talk);
        webSocketHandler.sendMessage(receiverId, wsMsg);
        webSocketHandler.sendMessage(senderId, wsMsg);
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
    public String talkMessageImage(@RequestBody ImageMsg imageMsg) {
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
