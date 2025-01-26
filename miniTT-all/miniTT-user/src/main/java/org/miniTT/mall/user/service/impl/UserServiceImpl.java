package org.miniTT.mall.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.miniTT.mall.common.convention.exception.ClientException;
import org.miniTT.mall.user.dao.entity.UserDO;
import org.miniTT.mall.user.dao.mapper.UserMapper;
import org.miniTT.mall.user.dto.req.LogoutReq;
import org.miniTT.mall.user.dto.req.RegisterReq;
import org.miniTT.mall.user.dto.req.UpdatePasswordReq;
import org.miniTT.mall.user.dto.resp.LogoutResp;
import org.miniTT.mall.user.dto.resp.RegisterResp;
import org.miniTT.mall.user.dto.req.LoginReq;
import org.miniTT.mall.user.dto.resp.LoginResp;
import org.miniTT.mall.user.service.UserService;
import org.miniTT.mall.user.session.SessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {


    @Autowired
    private SessionStore sessionStore; // 注入 SessionStore 实例
    @Transactional(rollbackFor = Exception.class)
    @Override
    // 注册
    public RegisterResp register(RegisterReq req) {
        if (req == null) {
            throw new ClientException("请求参数为空");
        }

        // 校验密码一致性
        if (!req.getPassword().equals(req.getConfirm_password())) {
            throw new ClientException("密码和确认密码不一致");
        }

        // 校验邮箱唯一性
        UserDO existingUser = this.lambdaQuery().eq(UserDO::getEmail, req.getEmail()).one();
        if (existingUser != null) {
            throw new ClientException("该邮箱已被注册");
        }

        // 创建用户
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

    @Override
    public LoginResp login(LoginReq req) {
        if (req == null) {
            throw new ClientException("请求参数为空");
        }

        // 查找用户
        UserDO userDO = this.lambdaQuery().eq(UserDO::getEmail, req.getEmail()).one();
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }

        // 校验密码
        if (!checkPassword(req.getPassword(), userDO.getPasswordHashed())) {
            throw new ClientException("密码错误");
        }

        // 登录成功后生成会话令牌
        StpUtil.login(userDO.getId());

        // 获取会话令牌
        String token = StpUtil.getTokenValue();

/*        // 将会话令牌存储到 sessionStore 中
        StpUtil.getSession().setToken(token);*/

        LoginResp resp = new LoginResp();
        resp.setUser_id(userDO.getId());
        resp.setToken(token); // 设置会话令牌
        return resp;
    }


    @Override
    public LogoutResp logout(LogoutReq req) {
        if (req == null) {
            throw new ClientException("请求参数为空");
        }

        // 查找用户的会话信息
        String storedToken = sessionStore.getToken(req.getUserId());
        if (storedToken == null || !storedToken.equals(req.getToken())) {
            throw new ClientException("无效的会话令牌");
        }

        // 清除用户的会话信息
        StpUtil.logout(req.getUserId());

        LogoutResp resp = new LogoutResp();
        resp.setSuccess(true);
        return resp;
    }

    private String generateToken(Long userId) {
        // 生成会话令牌的逻辑
        // 这里简单示例使用 UUID 生成令牌
        return UUID.randomUUID().toString();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(UpdatePasswordReq req) {
        // 获取请求中的新密码和确认密码
        String newPassword = req.getNewPassword();
        String confirmPassword = req.getConfirmPassword();
        String email = req.getEmail();
        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            throw new ClientException("新密码和确认密码不一致");
        }

        // 更新密码
        UserDO userDO = baseMapper.selectByEmail(email);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }

        userDO.setPasswordHashed(hashPassword(newPassword));
        int updated = baseMapper.updateByEmail(userDO);
        if (updated < 1) {
            throw new ClientException("密码更新失败");
        }
    }

    String hashPassword(String password) {
        // 使用 MD5 或其他哈希算法
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    private boolean checkPassword(String inputPassword, String storedPassword) {
        return storedPassword.equals(DigestUtils.md5DigestAsHex(inputPassword.getBytes()));
    }
}
