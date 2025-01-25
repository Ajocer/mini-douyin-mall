package org.miniTT.mall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.miniTT.mall.common.convention.exception.ClientException;
import org.miniTT.mall.user.dao.entity.UserDO;
import org.miniTT.mall.user.dao.mapper.UserMapper;
import org.miniTT.mall.user.dto.req.RegisterReq;
import org.miniTT.mall.user.dto.resp.RegisterResp;
import org.miniTT.mall.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResp register(RegisterReq req) {
        if (req == null) {
            throw new ClientException("请求参数为空");
        }

        if (!req.getPassword().equals(req.getConfirm_password())) {
            throw new ClientException("密码和确认密码不一致");
        }

        UserDO userDO = UserDO.builder()
                .email(req.getEmail())
                .passwordHashed(hashPassword(req.getPassword()))
                .build();

        int inserted = baseMapper.insert(userDO);
        if (inserted < 1) {
            throw new ClientException("注册失败");
        }

        RegisterResp resp = new RegisterResp();
        resp.setUser_id(userDO.getId());
        return resp;
    }

    private String hashPassword(String password) {
        // 这里可以使用实际的密码哈希算法，例如 BCrypt
        return password; // 示例中直接返回密码，实际应用中请替换为哈希后的密码
    }
}
