package cn.reghao.im.model.po;

import cn.reghao.jutil.jdk.db.BaseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author reghao
 * @date 2022-04-16 22:03:13
 */
@Getter
@Setter
public class Setting extends BaseObject<Integer> {
    private Long userId;
    private String keyboardEventNotify;
    private String notifyCueTone;
    private String themeBagImg;
    private String themeColor;
    private String themeMode;
}
