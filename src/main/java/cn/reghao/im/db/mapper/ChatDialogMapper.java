package cn.reghao.im.db.mapper;

import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.chat.ChatDialog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-18 18:00:31
 */
@Mapper
public interface ChatDialogMapper extends BaseMapper<ChatDialog> {
    void updateSetTop(long dialogId, long userId, boolean top);
    void updateSetDisturb(long dialogId, long userId, boolean disturb);
    void updateSetDisplay(long dialogId, long userId, boolean display);
    void updateSetClearUnread(long receiverId, long userId);
    void updateSetIncreaseUnread(long receiverId, long userId);

    void deleteGroupChatDialog(long groupId, List<Long> list);

    ChatDialog findByDialogAndUserId(long dialogId, long userId);
    ChatDialog findByReceiverAndUserId(long receiverId, long userId);
    @Deprecated
    ChatDialog findChatDialog(int chatType, long receiverId, long userId);
    List<ChatDialog> findChatDialogsByUserId(long userId);
}
