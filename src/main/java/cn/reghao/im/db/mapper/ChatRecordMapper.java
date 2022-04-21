package cn.reghao.im.db.mapper;

import cn.reghao.im.model.po.ChatRecord;
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

    List<ChatRecord> findByChatId(long chatId, long lastRecordId, int limit);
    ChatRecord findByRecordId(long recordId);
}
