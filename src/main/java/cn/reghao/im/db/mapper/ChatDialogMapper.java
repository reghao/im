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
    void updateSetDisturb(int dialogId, boolean disturb);
    void updateSetTop(int dialogId, boolean top);

    @Deprecated
    ChatDialog findByReceiverAndUserId(long receiverId, long userId);
    ChatDialog findChatDialog(int chatType, long receiverId, long userId);
    List<ChatDialog> findByUserId(long userId);
}
