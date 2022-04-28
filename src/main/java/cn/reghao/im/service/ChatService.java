package cn.reghao.im.service;

import cn.reghao.im.db.mapper.ChatDialogMapper;
import cn.reghao.im.db.mapper.ChatRecordMapper;
import cn.reghao.im.model.po.chat.ChatDialog;
import org.springframework.stereotype.Service;

/**
 * @author reghao
 * @date 2022-04-20 19:25:02
 */
@Service
public class ChatService {
    private final ChatDialogMapper chatDialogMapper;
    private ChatRecordMapper chatRecordMapper;

    public ChatService(ChatDialogMapper chatDialogMapper, ChatRecordMapper chatRecordMapper) {
        this.chatDialogMapper = chatDialogMapper;
        this.chatRecordMapper = chatRecordMapper;
    }

    public long getChatId(long senderId, long receiverId) {
        ChatDialog chatDialog = chatDialogMapper.findByReceiverAndUserId(receiverId, senderId);
        return chatDialog != null ? chatDialog.getChatId() : 0;
    }
}
