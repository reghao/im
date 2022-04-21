package cn.reghao.im.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-21 09:36:03
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PartUploadInitiate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileName;
    private long fileSize;
}
