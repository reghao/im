package cn.reghao.im.db.mapper;

import cn.reghao.im.model.po.FileInfo;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-21 17:45:40
 */
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    FileInfo findByFileId(String fileId);
}
