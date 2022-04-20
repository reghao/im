package cn.reghao.im.model.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-04-20 14:57:36
 */
public enum FileMsgType {
    image(1, "图片"), audio(2, "录音"), attachment(4, "附件");

    private final Integer code;
    private final String desc;

    private static Map<Integer, String> descMap = new HashMap<>();
    static {
        for (FileMsgType type : FileMsgType.values()) {
            descMap.put(type.code, type.desc);
        }
    }

    FileMsgType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    // TODO 第一次调用时会初始化 descMap
    public static String getDescByCode(int code) {
        return descMap.get(code);
    }
}
