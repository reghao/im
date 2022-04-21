package cn.reghao.im.controller;

import cn.reghao.im.model.dto.PartUploadInitiate;
import cn.reghao.im.model.vo.InitiateVo;
import cn.reghao.im.model.vo.PartUploadVo;
import cn.reghao.im.service.FileService;
import cn.reghao.im.util.WebResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-04-16 20:43:30
 */
@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadAvatar() {
        return WebResult.success();
    }

    @PostMapping(value = "/multipart/initiate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadMultipartInitiate(@RequestBody PartUploadInitiate partUploadInitiate) throws IOException {
        InitiateVo initiateVo = fileService.initFilePart(partUploadInitiate);
        return WebResult.success(initiateVo);
    }

    @PostMapping(value = "/multipart", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadMultipart(@RequestParam("file") MultipartFile file, @RequestParam("upload_id") String uploadId,
                                  @RequestParam("split_index") int splitIndex, @RequestParam("split_num") int splitNum)
            throws IOException {

        boolean ret = fileService.savePartFile(uploadId, splitIndex, splitNum, file);
        PartUploadVo partUploadVo  = new PartUploadVo(uploadId, ret);
        return WebResult.success(partUploadVo);
    }
}
