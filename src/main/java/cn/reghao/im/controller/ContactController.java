package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.UserAccountMapper;
import cn.reghao.im.db.mapper.UserContactMapper;
import cn.reghao.im.db.mapper.UserContactRecordMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.dto.ContactAdd;
import cn.reghao.im.model.po.UserContact;
import cn.reghao.im.model.po.UserContactRecord;
import cn.reghao.im.model.po.UserProfile;
import cn.reghao.im.model.vo.Paginate;
import cn.reghao.im.model.vo.UnreadNum;
import cn.reghao.im.model.vo.contact.*;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.model.dto.ContactAddRespond;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {
    private UserAccountMapper userAccountMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserContactMapper userContactMapper;
    private final UserContactRecordMapper userContactRecordMapper;

    public ContactController(UserAccountMapper userAccountMapper, UserProfileMapper userProfileMapper,
                             UserContactMapper userContactMapper, UserContactRecordMapper userContactRecordMapper) {
        this.userAccountMapper = userAccountMapper;
        this.userProfileMapper = userProfileMapper;
        this.userContactMapper = userContactMapper;
        this.userContactRecordMapper = userContactRecordMapper;
    }

    @ApiOperation(value = "查找联系人")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactSearch(@RequestParam("mobile") String mobile) {
        UserProfile userProfile = userProfileMapper.findByMobile(mobile);
        if (userProfile == null) {
            return WebResult.failWithMsg("查无此人");
        }

        SearchContactResult searchContactResult = new SearchContactResult();
        searchContactResult.setId(userProfile.getUserId());
        searchContactResult.setMobile(mobile);
        searchContactResult.setNickname(userProfile.getNickname());
        searchContactResult.setGender(userProfile.getGender());
        searchContactResult.setMotto(userProfile.getMotto());
        searchContactResult.setAvatar(userProfile.getAvatar());
        return WebResult.success(searchContactResult);
    }

    @ApiOperation(value = "获取联系人信息")
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactDetail(@RequestParam("user_id") long userId) {
        long currentUserId = Long.parseLong(Jwt.getUserInfo().getUserId());
        String mobile = userAccountMapper.findMobileByUserId(userId);
        UserProfile userProfile = userProfileMapper.findByUserId(userId);
        ContactInfoResult contactInfoResult = new ContactInfoResult();
        contactInfoResult.setId(userId);
        contactInfoResult.setMobile(mobile);
        contactInfoResult.setNickname(userProfile.getNickname());
        contactInfoResult.setGender(userProfile.getGender());
        contactInfoResult.setMotto(userProfile.getMotto());
        contactInfoResult.setAvatar(userProfile.getAvatar());

        ContactInfo contactInfo = userContactMapper.findByUserIdAndFriendId(currentUserId, userId);
        if (contactInfo != null) {
            contactInfoResult.setNicknameRemark(contactInfo.getFriendRemark());
            contactInfoResult.setFriendStatus(2);
            contactInfoResult.setFriendApply(2);
            return WebResult.success(contactInfoResult);
        }

        UserContactRecord userContactRecord = userContactRecordMapper.findByUserIdAndFriendId(currentUserId, userId);
        if (userContactRecord != null) {
            contactInfoResult.setFriendStatus(userContactRecord.getFriendStatus());
            contactInfoResult.setFriendApply(userContactRecord.getFriendApply());
        } else {
            contactInfoResult.setFriendStatus(1);
            contactInfoResult.setFriendApply(0);
        }
        return WebResult.success(contactInfoResult);
    }

    @ApiOperation(value = "申请添加联系人")
    @PostMapping(value = "/apply/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactApplyCreate(@RequestBody ContactAdd contactAdd) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        Long friendId = contactAdd.getFriendId();
        if (userContactRecordMapper.findByFriendId(friendId) != null) {
            return WebResult.successWithMsg("已发送申请, 请等待结果");
        }
        String remark = contactAdd.getRemark();

        UserContactRecord userContactRecord = new UserContactRecord();
        userContactRecord.setRequestUserId(userId);
        userContactRecord.setAddedUserId(friendId);
        userContactRecord.setFriendStatus(1);
        userContactRecord.setFriendApply(1);
        userContactRecord.setRemark(remark);
        userContactRecordMapper.save(userContactRecord);
        return WebResult.success();
    }

    @ApiOperation(value = "获取联系人申请列表")
    @GetMapping(value = "/apply/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactApplyRecords(@RequestParam("page") int page, @RequestParam("page_size") int pageSize) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ContactApplyInfo> list = userContactRecordMapper.findByFriendId1(userId);

        ContactApplyList contactApplyList = new ContactApplyList();
        contactApplyList.setRows(list);
        Paginate paginate = new Paginate();
        paginate.setPage(page);
        paginate.setSize(pageSize);
        paginate.setTotal(list.size());
        contactApplyList.setPaginate(paginate);

        return WebResult.success(contactApplyList);
    }

    @ApiOperation(value = "获取联系人申请未读消息数目")
    @GetMapping(value = "/apply/unread-num", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactApplyUnreadNum() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        int total = userContactRecordMapper.countFriendApply(userId);

        UnreadNum unreadNum = new UnreadNum(total);
        return WebResult.success(unreadNum);
    }

    @ApiOperation(value = "接受联系人申请")
    @PostMapping(value = "/apply/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactApplyAccept(@RequestBody ContactAddRespond contactAddRespond) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long requestUserId = contactAddRespond.getApplyId();
        String nicknameRemark = contactAddRespond.getRemark();

        UserContact userContact = new UserContact();
        userContact.setUserId(userId);
        userContact.setFriendId(requestUserId);
        userContact.setNicknameRemark(nicknameRemark);

        UserContact userContact1 = new UserContact();
        userContact1.setUserId(requestUserId);
        userContact1.setFriendId(userId);

        userContactRecordMapper.updateSetAccept(requestUserId, userId);
        userContactMapper.save(userContact);
        userContactMapper.save(userContact1);
        return WebResult.success();
    }

    @ApiOperation(value = "拒绝联系人申请")
    @PostMapping(value = "/apply/decline", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactApplyDecline(@RequestBody ContactAddRespond contactAddRespond) {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        long requestUserId = contactAddRespond.getApplyId();
        String remark = contactAddRespond.getRemark();

        return WebResult.success();
    }

    @ApiOperation(value = "获取联系人列表")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactList() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        List<ContactInfo> list = userContactMapper.findByUserId(userId);
        return WebResult.success(list);
    }

    @ApiOperation(value = "设置联系人备注")
    @PostMapping(value = "/edit-remark", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactEditRemark(@RequestParam("friend_id") int friendId, @RequestParam("remark") String remark) {
        return WebResult.success();
    }

    @ApiOperation(value = "删除联系人")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String contactDelete(@RequestParam("friend_id") int friendId) {
        return WebResult.success();
    }
}
