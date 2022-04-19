package cn.reghao.im.model.vo.contact;

import cn.reghao.im.model.vo.Paginate;
import lombok.Data;

import java.util.List;

/**
 * @author reghao
 * @date 2022-04-17 15:54:07
 */
@Data
public class ContactApplyList {
    private List<ContactApplyInfo> rows;
    private Paginate paginate;
}
