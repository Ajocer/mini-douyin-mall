package org.miniTT.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.miniTT.mall.user.dao.entity.UserDO;
import org.miniTT.mall.user.dto.req.RegisterReq;
import org.miniTT.mall.user.dto.resp.RegisterResp;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {
    RegisterResp register(RegisterReq req);
}
