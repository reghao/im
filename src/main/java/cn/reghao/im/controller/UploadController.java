package cn.reghao.im.controller;

import cn.reghao.im.util.WebResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
    @PostMapping(value = "/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadAvatar() {
        return WebResult.success();
    }

    @PostMapping(value = "/multipart/initiate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadMultipartInitiate() {
        return WebResult.success();
    }

    @PostMapping(value = "/multipart", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadMultipart() {
        return WebResult.success();
    }
}
