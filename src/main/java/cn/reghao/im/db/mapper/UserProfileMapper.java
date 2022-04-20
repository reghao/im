package cn.reghao.im.db.mapper;

import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.UserProfile;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-18 11:01:04
 */
@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {
    void updateSetStatus(long userId, boolean online);

    UserProfile findByMobile(String mobile);
    UserProfile findByUserId(long userId);
    UserInfo findUserInfoByUserId(long userId);
}
