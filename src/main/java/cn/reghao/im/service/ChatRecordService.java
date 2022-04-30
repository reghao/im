package cn.reghao.im.service;

import cn.reghao.im.db.mapper.ChatDialogMapper;
import cn.reghao.im.db.mapper.ChatRecordMapper;
import org.springframework.stereotype.Service;

/**
 * @author reghao
 * @date 2022-04-30 11:16:02
 */
@Service
public class ChatRecordService {
    private final ChatDialogMapper chatDialogMapper;
    private ChatRecordMapper chatRecordMapper;

    public ChatRecordService(ChatDialogMapper chatDialogMapper, ChatRecordMapper chatRecordMapper) {
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
    }


}
