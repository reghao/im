package cn.reghao.im.db.mapper;

import cn.reghao.im.model.dto.contact.ContactInfo;
import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.contact.UserContact;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-18 11:01:04
 */
@Mapper
public interface UserContactMapper extends BaseMapper<UserContact> {
    List<Long> getOnlineFriends(long userId);
    List<ContactInfo> findByUserId(long userId);
    ContactInfo findByUserIdAndFriendId(long userId, long friendId);
}
