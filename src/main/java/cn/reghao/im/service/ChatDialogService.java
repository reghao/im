package cn.reghao.im.service;

import cn.reghao.im.db.mapper.ChatDialogMapper;
import cn.reghao.im.db.mapper.ChatRecordMapper;
import cn.reghao.im.db.mapper.GroupInfoMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.constant.ChatType;
import cn.reghao.im.model.dto.chat.*;
import cn.reghao.im.model.po.chat.ChatDialog;
import cn.reghao.im.util.Jwt;
import cn.reghao.jutil.tool.id.SnowFlake;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-04-20 19:25:02
 */
@Service
public class ChatDialogService {
    private final SnowFlake snowFlake;
    private final ChatDialogMapper chatDialogMapper;
    private ChatRecordMapper chatRecordMapper;
    private final UserProfileMapper userProfileMapper;
    private final GroupInfoMapper groupInfoMapper;

    public ChatDialogService(ChatDialogMapper chatDialogMapper, ChatRecordMapper chatRecordMapper,
                             UserProfileMapper userProfileMapper, GroupInfoMapper groupInfoMapper) {
        this.snowFlake = new SnowFlake(1L, 1L);
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
        this.userProfileMapper = userProfileMapper;
        this.groupInfoMapper = groupInfoMapper;
    }

    public ChatInitialRet createChatDialog(ChatInitial chatInitial) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long receiverId = chatInitial.getReceiverId();
        int chatType = chatInitial.getTalkType();
        ChatDialog chatDialog;
        if (chatType == 1) {
            chatDialog = p2pChatDialog(receiverId, userId);
        } else {
            chatDialog = groupChatDialog(receiverId, userId);
        }

        if (!chatDialog.isDisplay()) {
            chatDialogMapper.updateSetDisplay(chatDialog.getId(), chatDialog.getUserId(), true);
        }

        ChatUserInfo chatUserInfo = getDialogUserInfo(userId, chatType, receiverId);
        return new ChatInitialRet(chatDialog, chatUserInfo);
    }

    private ChatDialog groupChatDialog(long receiverId, long userId) {
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        if (chatDialog == null) {
            long chatId = snowFlake.nextId();
            chatDialog = new ChatDialog(chatId, ChatType.single.getCode(), receiverId, userId, true);
            chatDialogMapper.save(chatDialog);
        }

        return chatDialog;
    }

    private ChatDialog p2pChatDialog(long receiverId, long userId) {
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        if (chatDialog == null) {
            long chatId = snowFlake.nextId();
            chatDialog = new ChatDialog(chatId, ChatType.single.getCode(), receiverId, userId, true);
            ChatDialog chatDialog1 = new ChatDialog(chatId, ChatType.single.getCode(), userId, receiverId, false);
            // TODO 保证批量操作全部成功 or 全部失败
            chatDialogMapper.saveAll(List.of(chatDialog, chatDialog1));
        }

        return chatDialog;
    }

    private ChatUserInfo getDialogUserInfo(long userId, int chatType, long receiverId) {
        ChatUserInfo chatUserInfo;
        if (chatType == 1) {
            chatUserInfo = userProfileMapper.findDialogFriendInfo(userId, receiverId);
        } else {
            chatUserInfo = groupInfoMapper.findDialogGroupInfo(receiverId);
        }

        return chatUserInfo;
    }

    public List<ChatInitialRet> getChatDialogs() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ChatDialog> chatDialogs = chatDialogMapper.findChatDialogsByUserId(userId);
        return chatDialogs.stream().map(chatDialog -> {
            int chatType = chatDialog.getChatType();
            long receiverId = chatDialog.getReceiverId();
            return new ChatInitialRet(chatDialog, getDialogUserInfo(userId, chatType, receiverId));
        }).collect(Collectors.toList());
    }

    public void deleteChatDialog(long dialogId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        chatDialogMapper.updateSetDisplay(dialogId, userId, false);
    }

    public void setChatDialogTop(ChatTop chatTop) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long dialogId = chatTop.getListId();
        int type = chatTop.getType();
        boolean top = type == 1;
        chatDialogMapper.updateSetTop(dialogId, userId, top);
    }

    public void setChatDialogDisturb(ChatDisturb chatDisturb) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        boolean disturb = chatDisturb.getIsDisturb() == 1;
        long receiverId = chatDisturb.getReceiverId();
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, userId);
        if (chatDialog != null) {
            chatDialogMapper.updateSetDisturb(chatDialog.getId(), userId, disturb);
        }
    }

    public void clearUnread(long receiverId) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
    }
}
