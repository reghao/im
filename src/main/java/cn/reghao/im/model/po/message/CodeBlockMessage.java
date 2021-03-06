package cn.reghao.im.model.po.message;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-21 16:06:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CodeBlockMessage extends BaseObject<Integer> {
    private long recordId;
    private String lang;
    private String code;
}
