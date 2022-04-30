package cn.reghao.im.db.mapper;

import cn.reghao.im.model.dto.chat.ChatRecordGetRet;
import cn.reghao.im.model.po.chat.ChatRecord;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-20 14:22:31
 */
@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
    void updateSetRevoke(long recordId);

    List<ChatRecordGetRet> findChatRecordGetRet(long receiverId, long userId, long lastRecordId, int limit);
    List<ChatRecordGetRet> findChatRecordGetRetByMsgType(int msgType, long receiverId, long userId, long lastRecordId, int limit);
    List<ChatRecord> findByChatId(long chatId, long lastRecordId, int limit);
    ChatRecord findByRecordId(long recordId);
}
