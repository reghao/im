package cn.reghao.im.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @author reghao
 * @date 2021-06-11 09:48:14
 */
@Data
public class AppLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String machineId;
    private String appId;
    private long timestamp;
    private String level;
    private String threadName;
    private String loggerName;
    private String message;
}
