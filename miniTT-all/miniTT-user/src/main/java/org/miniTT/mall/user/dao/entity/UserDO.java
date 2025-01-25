package org.miniTT.mall.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miniTT.mall.common.convention.database.BaseDO;

@Data
@TableName("user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDO extends BaseDO {

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String passwordHashed;

}
