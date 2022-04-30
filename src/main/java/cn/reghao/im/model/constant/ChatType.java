package cn.reghao.im.model.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-04-17 17:23:17
 */
public enum ChatType {
    single(1, "p2p 聊天"), group(2, "群组聊天");

    private final Integer code;
    private final String desc;

    private static Map<Integer, String> descMap = new HashMap<>();
    static {
        for (ChatType gender : ChatType.values()) {
            descMap.put(gender.code, gender.desc);
        }
    }

    ChatType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // TODO 第一次调用时会初始化 descMap
    public static String getDescByCode(int code) {
        return descMap.get(code);
    }

    public Integer getCode() {
        return code;
    }
}
