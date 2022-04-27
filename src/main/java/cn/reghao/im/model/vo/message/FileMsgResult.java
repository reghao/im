package cn.reghao.im.model.vo.message;

import cn.reghao.im.model.po.FileInfo;
import cn.reghao.im.model.po.FileMessage;
import cn.reghao.tnb.file.api.dto.FileInfoDto;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-20 14:18:23
 */
@Getter
public class FileMsgResult {
    private String fileId;
    private long recordId;
    private long userId;
    private int drive;
    private int id;
    private String originalName;
    private String path;
    private long size;
    private int source;
    private String suffix;
    private int type;
    private String url;
    private String createdAt;

    public FileMsgResult(FileMessage fileMessage, FileInfoDto fileInfoDto, long userId, String createdAt) {
        this.fileId = fileMessage.getUploadId();
        this.recordId = fileMessage.getRecordId();
        this.userId = userId;
        this.drive = 1;
        this.id = fileMessage.getId();
        this.originalName = fileInfoDto.getFilename();
        this.path = fileInfoDto.getUrl();
        this.size = fileInfoDto.getSize();
        this.source = 1;
        this.suffix = fileInfoDto.getSuffix();
        this.type = fileInfoDto.getFileType();;
        this.url = fileInfoDto.getUrl();
        this.createdAt = createdAt;
    }
}
