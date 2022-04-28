package cn.reghao.im.controller;

import cn.reghao.im.model.dto.PartUploadInitiate;
import cn.reghao.im.model.po.FileInfo;
import cn.reghao.im.model.vo.InitiateVo;
import cn.reghao.im.model.vo.PartUploadVo;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.util.WebResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/upload")
@Deprecated
public class UploadController {
    @PostMapping(value = "/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        long userId = Long.parseLong(Jwt.getUserInfo().getUserId());
        FileInfo fileInfo = null;
        String avatarUrl = fileInfo.getUrl();
        //userProfileMapper.updateSetAvatar(userId, avatarUrl);

        Map<String, String> map = new HashMap<>();
        map.put("avatar", fileInfo.getUrl());
        return WebResult.success(map);
    }

    @PostMapping(value = "/multipart/initiate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadMultipartInitiate(@RequestBody PartUploadInitiate partUploadInitiate) throws IOException {
        InitiateVo initiateVo = new InitiateVo("1", 1);
        return WebResult.success(initiateVo);
    }

    @PostMapping(value = "/multipart", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadMultipart(@RequestParam("file") MultipartFile file, @RequestParam("upload_id") String uploadId,
                                  @RequestParam(value = "sha256sum", required = false) String sha256sum,
                                  @RequestParam("split_index") int splitIndex, @RequestParam("split_num") int splitNum)
            throws IOException {
        PartUploadVo partUploadVo  = new PartUploadVo(uploadId, false);
        return WebResult.success(partUploadVo);
    }
}
