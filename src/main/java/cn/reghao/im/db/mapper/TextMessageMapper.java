package cn.reghao.im.db.mapper;

import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.TextMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-18 18:00:31
 */
@Mapper
public interface TextMessageMapper extends BaseMapper<TextMessage> {
    List<TextMessage> findBySenderAndReceiverId(long senderId, long receiverId);
}
