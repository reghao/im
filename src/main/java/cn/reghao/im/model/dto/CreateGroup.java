package cn.reghao.im.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2022-04-21 20:37:31
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String avatar;
    private String ids;
    private String name;
    private String profile;
}
