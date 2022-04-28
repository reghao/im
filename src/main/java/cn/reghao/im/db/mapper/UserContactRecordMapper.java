package cn.reghao.im.db.mapper;

import cn.reghao.im.model.dto.contact.ContactApplyInfo;
import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.contact.UserContactRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-18 15:32:55
 */
@Mapper
public interface UserContactRecordMapper extends BaseMapper<UserContactRecord> {
    void updateSetAccept(long requestUserId, long addedUserId);

    UserContactRecord findByUserIdAndFriendId(long userId, long friendId);
    UserContactRecord findByFriendId(long friendId);
    int countFriendApply(long friendId);
    List<ContactApplyInfo> findByFriendId1(long friendId);
}
