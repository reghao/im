package cn.reghao.im.model.vo;

import lombok.Data;

/**
 * @author reghao
 * @date 2022-04-17 15:55:52
 */
@Data
public class Paginate {
    private int page;
    private int size;
    private int total;
}
