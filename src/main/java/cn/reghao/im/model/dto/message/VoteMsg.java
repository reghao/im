package cn.reghao.im.model.dto.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author reghao
 * @date 2022-04-17 16:57:00
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VoteMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private int receiverId;
    private String title;
    private int mode;
    private List<String> options;
}
