package cn.reghao.im.model.po.message;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author reghao
 * @date 2022-04-18 17:58:48
 */
@NoArgsConstructor
@Getter
public class TextMessage extends BaseObject<Integer> {
    private long recordId;
    private String content;

    public TextMessage(long recordId, String content) {
        this.recordId = recordId;
        this.content = content;
    }
}
