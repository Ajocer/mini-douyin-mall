package org.miniTT.mall.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.miniTT.mall.user.dao.entity.UserDO;
import org.apache.ibatis.annotations.Select;

/**
 * 用户数据访问层
 */
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 通过邮箱查询用户
     * @param email 用户邮箱
     * @return UserDO
     */
    @Select("SELECT * FROM user WHERE email = #{email}")
    UserDO selectByEmail(String email);
}
