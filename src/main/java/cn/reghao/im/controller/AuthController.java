package cn.reghao.im.controller;

import cn.reghao.im.db.mapper.UserAccountMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.dto.LoginDto;
import cn.reghao.im.model.po.UserAccount;
import cn.reghao.im.model.po.UserProfile;
import cn.reghao.im.model.vo.auth.LoginRetDto;
import cn.reghao.im.model.vo.user.UserInfo;
import cn.reghao.im.util.WebResult;
import cn.reghao.im.ws.WebSocketHandlerImpl;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.util.JwtPayload;
import cn.reghao.im.model.dto.UserRegisterDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final WebSocketHandlerImpl webSocketHandler;
    private final UserAccountMapper userAccountMapper;
    private final UserProfileMapper userProfileMapper;

    public AuthController(WebSocketHandlerImpl webSocketHandler, UserAccountMapper userAccountMapper,
                          UserProfileMapper userProfileMapper) {
        this.webSocketHandler = webSocketHandler;
        this.userAccountMapper = userAccountMapper;
        this.userProfileMapper = userProfileMapper;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authRegister(@RequestBody UserRegisterDto userRegisterDto) {
        Long maxUserId = userAccountMapper.findByMaxUserId();
        if (maxUserId == null) {
            maxUserId = 10000L;
        } else {
            maxUserId++;
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(maxUserId);
        userAccount.setMobile(userRegisterDto.getMobile());
        userAccount.setPassword(userRegisterDto.getPassword());
        userAccount.setPlatform(userRegisterDto.getPlatform());

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(maxUserId);
        userProfile.setNickname(userRegisterDto.getNickname());

        userAccountMapper.save(userAccount);
        userProfileMapper.save(userProfile);
        return WebResult.success();
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authLogin(@RequestBody LoginDto loginDto) throws IOException {
        UserAccount userAccount = userAccountMapper.findByMobile(loginDto.getMobile());
        long userId = userAccount.getUserId();
        long expire = 86400;
        Date date = Date.from(Instant.ofEpochSecond(expire));
        JwtPayload jwtPayload = new JwtPayload(String.valueOf(userId), "user_role", date);
        String jwtToken = Jwt.create(jwtPayload);

        UserProfile userProfile = userProfileMapper.findByUserId(userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(userProfile.getUserId());
        userInfo.setNickname(userProfile.getNickname());
        userInfo.setGender(userProfile.getGender());
        userInfo.setSignature(userProfile.getMotto());
        userInfo.setAvatar(userProfile.getAvatar());

        LoginRetDto loginRetDto = new LoginRetDto();
        loginRetDto.setAccessToken(jwtToken);
        loginRetDto.setExpiresIn(expire);
        loginRetDto.setType("Bearer");
        loginRetDto.setUserInfo(userInfo);
        return WebResult.success(loginRetDto);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authLogout() {
        return WebResult.success();
    }

    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authRefreshToken() {
        return WebResult.success();
    }

    @PostMapping(value = "/forget", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authForget() {
        return WebResult.success();
    }
}
