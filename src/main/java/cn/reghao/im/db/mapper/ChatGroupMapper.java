package cn.reghao.im.db.mapper;

import cn.reghao.im.model.po.contact.ChatGroup;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-21 20:52:07
 */
@Mapper
public interface ChatGroupMapper extends BaseMapper<ChatGroup> {
    void updateChatGroup(ChatGroup chatGroup);

    ChatGroup findByGroupId(long groupId);
    List<ChatGroup> findByGroupIds(long userId);
    List<Long> findUserIdsByGroupId(long groupId);
}
