package cn.reghao.im.model.vo.message;

import cn.reghao.im.model.po.FileInfo;
import cn.reghao.im.model.po.FileMessage;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-20 14:18:23
 */
@Getter
public class FileMsgResult {
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

    public FileMsgResult(FileMessage fileMessage, FileInfo fileInfo, long userId, String createdAt) {
        this.recordId = fileMessage.getRecordId();
        this.userId = userId;
        this.drive = fileInfo.getDrive();
        this.id = fileMessage.getId();
        this.originalName = fileInfo.getFilename();
        this.path = fileInfo.getUrl();
        this.size = fileInfo.getSize();
        this.source = fileInfo.getSource();
        this.suffix = fileInfo.getSuffix();
        this.type = fileInfo.getFileType();;
        this.url = fileInfo.getUrl();
        this.createdAt = createdAt;
    }
}
