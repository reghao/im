package cn.reghao.im.model.dto.emoticon;

import lombok.Data;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-17 16:13:58
 */
@Data
public class EmoticonList {
    private List<EmoticonInfo> collectEmoticon;
    private List<EmoticonInfo> sysEmoticon;
}
