package cn.reghao.im.db.mapper;

import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.message.TextMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-18 18:00:31
 */
@Mapper
public interface TextMessageMapper extends BaseMapper<TextMessage> {
    TextMessage findByRecordId(long recordId);
}
