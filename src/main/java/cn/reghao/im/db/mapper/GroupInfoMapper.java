package cn.reghao.im.db.mapper;

import cn.reghao.im.model.dto.group.GroupDetailRet;
import cn.reghao.im.model.dto.group.GroupInfoRet;
import cn.reghao.im.model.po.group.GroupInfo;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-21 20:52:07
 */
@Mapper
public interface GroupInfoMapper extends BaseMapper<GroupInfo> {
    void updateGroupInfo(GroupInfo groupInfo);

    GroupInfo findByGroupId(long groupId);
    GroupDetailRet findDetailByGroupId(long groupId);
    List<GroupInfoRet> findGroupsByUserId(long userId);
}
