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
    RegisterResp register(RegisterReq req);
    LoginResp login(LoginReq req);

    LogoutResp logout(LogoutReq req);

    void updatePassword(Long userId, String newPassword, String confirmPassword);
}
