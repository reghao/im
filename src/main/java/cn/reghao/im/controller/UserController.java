package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.SettingMapper;
import cn.reghao.im.db.mapper.UserAccountMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.po.Setting;
import cn.reghao.im.model.dto.user.UserInfo;
import cn.reghao.im.model.dto.user.UserSetting;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.util.Jwt;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/users")
@Deprecated
public class UserController {
    private final UserAccountMapper userAccountMapper;
    private final UserProfileMapper userProfileMapper;
    private final SettingMapper settingMapper;

    public UserController(UserAccountMapper userAccountMapper, SettingMapper settingMapper,
                          UserProfileMapper userProfileMapper) {
        this.userAccountMapper = userAccountMapper;
        this.settingMapper = settingMapper;
        this.userProfileMapper = userProfileMapper;
    }

    @ApiOperation(value = "获取用户信息设置")
    @GetMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_VALUE)
    public String usersSetting() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        Setting setting = settingMapper.findByUserId(userId);
        UserInfo userInfo = userProfileMapper.findUserInfoByUserId(userId);
        UserSetting userSetting = new UserSetting();
        userSetting.setSetting(setting);
        userSetting.setUserInfo(userInfo);
        return WebResult.success(userSetting);
    }

    @PostMapping(value = "/change/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public String usersChangePassword() {
        return WebResult.success();
    }

    @PostMapping(value = "/change/mobile", produces = MediaType.APPLICATION_JSON_VALUE)
    public String usersChangeMobile() {
        return WebResult.success();
    }

    @PostMapping(value = "/change/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public String usersChangeEmail() {
        return WebResult.success();
    }

    @PostMapping(value = "/change/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String usersChangeDetail() {
        return WebResult.success();
    }

    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String usersDetail() {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        return WebResult.success();
    }
}
