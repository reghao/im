package cn.reghao.im.model.dto.message;

import cn.reghao.im.model.po.message.CodeBlockMessage;
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

    public CodeBlockResult(CodeBlockMessage codeBlockMessage, long senderId) {
        this.id = codeBlockMessage.getId();
        this.userId = senderId;
        this.recordId = codeBlockMessage.getRecordId();
        this.lang = codeBlockMessage.getLang();
        this.code = codeBlockMessage.getCode();
        this.ceatedAt = DateTimeConverter.format(codeBlockMessage.getCreateTime());
    }
}
