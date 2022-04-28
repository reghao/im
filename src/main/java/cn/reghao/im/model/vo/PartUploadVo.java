package cn.reghao.im.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author reghao
 * @date 2022-04-21 09:32:00
 */
@AllArgsConstructor
@Deprecated
public class PartUploadVo {
    private String uploadId;
    private boolean isMerge;
}
