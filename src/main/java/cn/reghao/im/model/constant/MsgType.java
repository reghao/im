package cn.reghao.im.model.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 聊天消息类型
 *
 * @author reghao
 * @date 2022-04-17 16:47:55
 */
public enum MsgType {
    all(0, "所有消息"), text(1, "文本消息"), media(2, "音频/视频/图片/文件消息"),
    chatRecord(3, "聊天记录"), codeBlock(4, "代码块"), vote(5, "投票");

    private final Integer code;
    private final String desc;

    private static Map<Integer, String> descMap = new HashMap<>();
    static {
        for (MsgType gender : MsgType.values()) {
            descMap.put(gender.code, gender.desc);
        }
    }

    MsgType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // TODO 第一次调用时会初始化 descMap
    public static String getDescByCode(int code) {
        return descMap.get(code);
    }
}
