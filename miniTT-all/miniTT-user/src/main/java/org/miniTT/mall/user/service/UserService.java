package org.miniTT.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.miniTT.mall.user.dao.entity.UserDO;
import org.miniTT.mall.user.dto.req.LogoutReq;
import org.miniTT.mall.user.dto.req.RegisterReq;
import org.miniTT.mall.user.dto.resp.LogoutResp;
import org.miniTT.mall.user.dto.resp.RegisterResp;
import org.miniTT.mall.user.dto.req.LoginReq;
import org.miniTT.mall.user.dto.resp.LoginResp;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    // 用户注册接口
    RegisterResp register(RegisterReq req);

    // 用户登录接口
    LoginResp login(LoginReq req);


    // 用户登出接口
    LogoutResp logout(LogoutReq req);


    // 修改密码接口
    void updatePassword(Long userId, String newPassword, String confirmPassword);
}
