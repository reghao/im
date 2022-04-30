package cn.reghao.im.db.mapper;

import cn.reghao.im.model.po.message.CodeBlockMessage;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-21 16:08:23
 */
@Mapper
public interface CodeBlockMessageMapper extends BaseMapper<CodeBlockMessage> {
    CodeBlockMessage findByRecordId(long recordId);
}
