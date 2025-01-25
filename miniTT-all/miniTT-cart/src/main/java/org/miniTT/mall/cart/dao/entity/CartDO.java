package org.miniTT.mall.cart.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.miniTT.mall.common.convention.database.BaseDO;


@Data
@TableName("cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDO extends BaseDO {

    private Integer userId;

    private Integer productId;

    private Integer qty;

}
