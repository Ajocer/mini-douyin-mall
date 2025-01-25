package org.miniTT.mall.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.miniTT.mall.common.convention.database.BaseDO;

@Data
@TableName("user")
public class UserDO extends BaseDO {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String passwordHashed;

}
