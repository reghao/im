package cn.reghao.im.db.mapper;

import cn.reghao.im.model.dto.group.GroupMemberRet;
import cn.reghao.im.model.po.group.GroupMember;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-21 20:52:07
 */
@Mapper
public interface GroupMemberMapper extends BaseMapper<GroupMember> {
    void updateSetMemberRemark(long groupId, long userId, String nickname);
    void deleteGroupMembers(long groupId, List<Long> list);

    GroupMember findByGroupAndUserId(long groupId, long userId);
    List<Long> findUserIdsByGroupId(long groupId);
    List<GroupMemberRet> findByGroupId(long groupId);
}
