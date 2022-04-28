package cn.reghao.im.db.mapper;

import cn.reghao.im.model.po.chat.Chat;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-20 10:45:02
 */
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
    Chat findByChatId(long chatId);
}
