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
public class FileMessage extends BaseObject<Integer> {
    private long recordId;
    private String uploadId;

    public FileMessage(long recordId, String uploadId) {
        this.recordId = recordId;
        this.uploadId = uploadId;
    }
}
