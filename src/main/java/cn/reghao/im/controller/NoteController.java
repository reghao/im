package cn.reghao.im.controller;

import cn.reghao.im.util.WebResult;
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
@RequestMapping("/api/v1/note")
public class NoteController {
    @GetMapping(value = "/article/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleList() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/editor", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleEditor() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleDelete() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/forever/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleForeverDelete() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/recover", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleRecover() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/asterisk", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleAsterisk() {
        return WebResult.success();
    }

    @GetMapping(value = "/article/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleDetail() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/move", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleMove() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/upload/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleUploadImage() {
        return WebResult.success();
    }

    @PostMapping(value = "/article/tag", produces = MediaType.APPLICATION_JSON_VALUE)
    public String articleTag() {
        return WebResult.success();
    }

    @GetMapping(value = "/class/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String classList() {
        return WebResult.success();
    }

    @PostMapping(value = "/class/editor", produces = MediaType.APPLICATION_JSON_VALUE)
    public String classEditor() {
        return WebResult.success();
    }

    @PostMapping(value = "/class/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String classDelete() {
        return WebResult.success();
    }

    @PostMapping(value = "/class/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    public String classSort() {
        return WebResult.success();
    }

    @PostMapping(value = "/class/merge", produces = MediaType.APPLICATION_JSON_VALUE)
    public String classMerge() {
        return WebResult.success();
    }

    @GetMapping(value = "/tag/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String tagList() {
        return WebResult.success();
    }

    @PostMapping(value = "/tag/editor", produces = MediaType.APPLICATION_JSON_VALUE)
    public String tagEditor() {
        return WebResult.success();
    }

    @PostMapping(value = "/tag/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String tagDelete() {
        return WebResult.success();
    }

    @PostMapping(value = "/annex/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annexUpload() {
        return WebResult.success();
    }

    @PostMapping(value = "/annex/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annexDelete() {
        return WebResult.success();
    }

    @PostMapping(value = "/annex/forever/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annexForeverDelete() {
        return WebResult.success();
    }

    @PostMapping(value = "/annex/recover", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annexRecover() {
        return WebResult.success();
    }

    @PostMapping(value = "/annex/recover/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annexRecoverList() {
        return WebResult.success();
    }

    @GetMapping(value = "/annex/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annexDownload() {
        return WebResult.success();
    }
}
