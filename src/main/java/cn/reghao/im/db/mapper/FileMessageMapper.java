package cn.reghao.im.db.mapper;

import cn.reghao.im.model.po.FileMessage;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-20 15:30:47
 */
@Mapper
public interface FileMessageMapper extends BaseMapper<FileMessage> {
    FileMessage findByRecordId(long recordId);
}
