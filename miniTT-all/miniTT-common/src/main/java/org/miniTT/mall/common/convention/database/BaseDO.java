package org.miniTT.mall.common.convention.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date created_at;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated_at;
}
