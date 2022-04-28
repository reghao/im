package cn.reghao.im.ws.model.resp;

import lombok.AllArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-19 16:07:53
 */
@AllArgsConstructor
public class EvtLoginResp {
    private long userId;
    private int status;

    public EvtLoginResp(long userId, boolean online) {
        this.userId = userId;
        this.status = online ? 1 : 0;
    }
}
