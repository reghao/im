package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-20 14:18:23
 */
@NoArgsConstructor
@Getter
@Setter
public class FileInfo extends BaseObject<Integer> {
    private String fileId;
    private String filename;
    private String suffix;
    private long size;
    // 1 - 图片文件, 2 - 录音文件, 4 - 上传的附件(非图片)
    private int fileType;
    private String url;
    private int drive;
    private int source;

    public FileInfo(String fileId, String filename, String suffix, long size, int fileType, String url) {
        this.fileId = fileId;
        this.filename = filename;
        this.suffix = suffix;
        this.size = size;
        this.fileType = fileType;
        this.url = url;
        this.drive = 1;
        this.source = 1;
    }
}
