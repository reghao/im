package cn.reghao.im.controller;

import cn.reghao.im.model.dto.emoticon.EmoticonInfo;
import cn.reghao.im.model.dto.emoticon.EmoticonList;
import cn.reghao.im.util.WebResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/emoticon")
public class EmoticonController {
    @ApiOperation(value = "获取颜文字图标列表")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String emoticonList() {
        EmoticonList emoticonList = new EmoticonList();
        List<EmoticonInfo> collectEmoticon = new ArrayList<>();
        collectEmoticon.add(new EmoticonInfo(1, "https://im.gzydong.club/public/media/image/talk/20220413/cb75008fbe6b8e372b0356ed6ddb73cb_456x360.png"));
        emoticonList.setCollectEmoticon(collectEmoticon);

        List<EmoticonInfo> systemEmoticon = new ArrayList<>();
        emoticonList.setSysEmoticon(systemEmoticon);
        return WebResult.success(emoticonList);
    }

    @GetMapping(value = "/system/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String emoticonSystemList() {
        return WebResult.success();
    }

    @PostMapping(value = "/system/install", produces = MediaType.APPLICATION_JSON_VALUE)
    public String emoticonSystemInstall() {
        return WebResult.success();
    }

    @PostMapping(value = "/system/del-collect-emoticon", produces = MediaType.APPLICATION_JSON_VALUE)
    public String emoticonSystemDelCollectEmoticon() {
        return WebResult.success();
    }

    @PostMapping(value = "/customize/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String emoticonCustomizeCreate() {
        return WebResult.success();
    }
}
