package cn.reghao.im.controller;

import cn.reghao.im.util.WebResult;
import cn.reghao.im.model.dto.SmsCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/common")
public class CommonController {
    @PostMapping(value = "/sms-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String smsCode(@RequestBody SmsCode smsCode) {
        return WebResult.success();
    }

    @PostMapping(value = "/email-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String emailCode() {
        return WebResult.success();
    }
}
