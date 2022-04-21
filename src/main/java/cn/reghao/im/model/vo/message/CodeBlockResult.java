package cn.reghao.im.model.vo.message;

import cn.reghao.im.model.dto.message.CodeBlockMsg;
import cn.reghao.im.model.po.CodeMessage;
import cn.reghao.jutil.jdk.converter.DateTimeConverter;

/**
 * @author reghao
 * @date 2022-04-21 16:15:11
 */
public class CodeBlockResult {
    private int id;
    private long userId;
    private long recordId;
    private String lang;
    private String code;
    private String ceatedAt;

    public CodeBlockResult(CodeMessage codeMessage, long senderId) {
        this.id = codeMessage.getId();
        this.userId = senderId;
        this.recordId = codeMessage.getRecordId();
        this.lang = codeMessage.getLang();
        this.code = codeMessage.getCode();
        this.ceatedAt = DateTimeConverter.format(codeMessage.getCreateTime());
    }
}
