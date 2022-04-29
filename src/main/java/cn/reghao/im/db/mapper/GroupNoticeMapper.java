package cn.reghao.im.db.mapper;

import cn.reghao.im.model.dto.group.NoticeRet;
import cn.reghao.im.model.po.group.GroupNotice;
import cn.reghao.jutil.jdk.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-28 17:16:17
 */
@Mapper
public interface GroupNoticeMapper extends BaseMapper<GroupNotice> {
    void updateSetNotice(GroupNotice groupNotice);

    GroupNotice findByNoticeId(int noticeId);
    List<NoticeRet> findByGroupId(long groupId);
    NoticeRet findByLatest(long groupId);
}
