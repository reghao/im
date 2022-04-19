package cn.reghao.im.db.mapper;

import cn.reghao.jutil.jdk.db.BaseMapper;
import cn.reghao.im.model.po.UserAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author reghao
 * @date 2022-04-18 11:01:04
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    Long findByMaxUserId();
    UserAccount findByMobile(String mobile);
    String findMobileByUserId(long userId);
}
