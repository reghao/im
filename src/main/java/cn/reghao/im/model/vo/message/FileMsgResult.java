package cn.reghao.im.model.vo.message;

import cn.reghao.im.model.po.FileMessage;

/**
 * @author reghao
 * @date 2022-04-20 14:18:23
 */
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

    public FileMsgResult(FileMessage fileMessage, long userId, String createdAt) {
        this.recordId = fileMessage.getRecordId();
        this.userId = userId;
        this.drive = fileMessage.getDrive();
        this.id = fileMessage.getId();
        this.originalName = fileMessage.getFilename();
        this.path = fileMessage.getUrl();
        this.size = fileMessage.getSize();
        this.source = fileMessage.getSource();
        this.suffix = fileMessage.getSuffix();
        this.type = fileMessage.getFileType();;
        this.url = fileMessage.getUrl();
        this.createdAt = createdAt;
    }
}
