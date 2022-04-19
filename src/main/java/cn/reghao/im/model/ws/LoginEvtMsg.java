package cn.reghao.im.model.ws;

import lombok.AllArgsConstructor;

/**
 * @author reghao
 * @date 2022-04-19 16:07:53
 */
@AllArgsConstructor
public class LoginEvtMsg {
    private long userId;
    private boolean status;
}
