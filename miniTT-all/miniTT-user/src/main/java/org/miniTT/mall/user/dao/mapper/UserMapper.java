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

    /**
     * 注册用户
     * @param userDO 用户信息
     * @return 影响行数
     */
    @Select("insert into user(email, password_hashed, created_at, updated_at) values(#{email}, #{password_hashed}, #{created_at}, #{updated_at})")
    int insert(UserDO userDO);

    /**
     * 更新用户信息
     * @param userDO 用户信息
     * @return 影响行数
     */
    @Select("update user set password_hashed = #{password_hashed}, updated_at = #{updated_at} where email = #{email}")
    int updateByEmail(UserDO userDO);




}
