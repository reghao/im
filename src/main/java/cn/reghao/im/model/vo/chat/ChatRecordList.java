package cn.reghao.im.model.vo.chat;

import lombok.Data;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-17 16:18:31
 */
@Data
public class ChatRecordList {
    private int recordId;
    private int limit;
    private List<ChatRecord> rows;
}
