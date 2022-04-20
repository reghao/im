package cn.reghao.im.model.ws;

import lombok.AllArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-19 16:07:53
 */
@AllArgsConstructor
public class LoginEvent {
    private long userId;
    private int status;

    public LoginEvent(long userId, boolean online) {
        this.userId = userId;
        this.status = online ? 1 : 0;
    }
}
