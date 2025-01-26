package org.miniTT.mall.user.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.miniTT.mall.common.convention.exception.ClientException;
import org.miniTT.mall.user.dao.entity.UserDO;
import org.miniTT.mall.user.dao.mapper.UserMapper;
import org.miniTT.mall.user.dto.req.LoginReq;
import org.miniTT.mall.user.dto.req.LogoutReq;
import org.miniTT.mall.user.dto.req.RegisterReq;
import org.miniTT.mall.user.dto.resp.LoginResp;
import org.miniTT.mall.user.dto.resp.LogoutResp;
import org.miniTT.mall.user.dto.resp.RegisterResp;
import org.miniTT.mall.user.service.UserService;
import org.miniTT.mall.user.session.SessionStore;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试注册
    @Test
    void testRegister_正常情况() {
        // 准备测试数据
        RegisterReq req = new RegisterReq();
        req.setEmail("test@example.com");
        req.setPassword("password123");
        req.setConfirm_password("password123");

        // 模拟数据库操作
        when(userMapper.selectOne(any())).thenReturn(null);
        when(userMapper.insert(any(UserDO.class))).thenReturn(1);

        // 执行被测方法
        RegisterResp resp = userService.register(req);

        // 验证结果
        assertNotNull(resp);
//        assertNotNull(resp.getUser_id());

        // 验证数据库操作
        verify(userMapper, times(1)).selectOne(any());
        verify(userMapper, times(1)).insert(any(UserDO.class));
    }
    // 测试注册
    @Test
    void testRegister_请求参数为空() {
        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.register(null);
        });

        // 验证异常信息
        assertEquals("请求参数为空", exception.getMessage());

        // 验证数据库操作没有发生
        verify(userMapper, never()).selectOne(any());
        verify(userMapper, never()).insert(any(UserDO.class));
    }

    @Test
    void testRegister_密码不一致() {
        // 准备测试数据
        RegisterReq req = new RegisterReq();
        req.setEmail("test@example.com");
        req.setPassword("password123");
        req.setConfirm_password("differentPassword");

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.register(req);
        });

        // 验证异常信息
        assertEquals("密码和确认密码不一致", exception.getMessage());

        // 验证数据库操作没有发生
        verify(userMapper, never()).selectOne(any());
        verify(userMapper, never()).insert(any(UserDO.class));
    }

    @Test
    void testRegister_邮箱已被注册() {
        // 准备测试数据
        RegisterReq req = new RegisterReq();
        req.setEmail("123456789@example.com");
        req.setPassword("password123");
        req.setConfirm_password("password123");

        // 模拟数据库操作
        UserDO existingUser = UserDO.builder().id(1L).build();
        when(userMapper.selectOne(any())).thenReturn(existingUser);

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.register(req);
        });

        // 验证异常信息
        assertEquals("该邮箱已被注册", exception.getMessage());

        // 验证数据库操作
        verify(userMapper, times(1)).selectOne(any());
        verify(userMapper, never()).insert(any(UserDO.class));
    }

    @Test
    void testRegister_插入失败() {
        // 准备测试数据
        RegisterReq req = new RegisterReq();
        req.setEmail("test@example.com");
        req.setPassword("password123");
        req.setConfirm_password("password123");

        // 模拟数据库操作
        when(userMapper.selectOne(any())).thenReturn(null);
        when(userMapper.insert(any(UserDO.class))).thenReturn(0);

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.register(req);
        });

        // 验证异常信息
        assertEquals("注册失败", exception.getMessage());

        // 验证数据库操作
        verify(userMapper, times(1)).selectOne(any());
        verify(userMapper, times(1)).insert(any(UserDO.class));
    }

    @Test
    void testLogin_正常情况() {
        // 准备测试数据
        LoginReq req = new LoginReq();
        req.setEmail("123456789@example.com");
        String plainPassword = "123456";
        req.setPassword(plainPassword);

        // 模拟数据库操作
        String hashedPassword = userService.hashPassword(plainPassword); // 调用 hashPassword 方法对密码进行加密
        UserDO userDO = UserDO.builder()
                .id(1L)
                .passwordHashed(hashedPassword) // 使用加密后的密码
                .build();
        when(userMapper.selectOne(any())).thenReturn(userDO);

        // 执行被测方法
        LoginResp resp = userService.login(req);

        // 验证结果
        assertNotNull(resp);
        assertEquals(1L, resp.getUser_id());

        // 验证数据库操作
        verify(userMapper, times(1)).selectOne(any());
    }


    @Test
    void testLogin_请求参数为空() {
        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.login(null);
        });

        // 验证异常信息
        assertEquals("请求参数为空", exception.getMessage());

        // 验证数据库操作没有发生
        verify(userMapper, never()).selectOne(any());
    }

    @Test
    void testLogin_用户不存在() {
        // 准备测试数据
        LoginReq req = new LoginReq();
        req.setEmail("test@example.com");
        req.setPassword("password123");

        // 模拟数据库操作
        when(userMapper.selectOne(any())).thenReturn(null);

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.login(req);
        });

        // 验证异常信息
        assertEquals("用户不存在", exception.getMessage());

        // 验证数据库操作
        verify(userMapper, times(1)).selectOne(any());
    }

    @Test
    void testLogin_密码错误() {
        // 准备测试数据
        LoginReq req = new LoginReq();
        req.setEmail("123456789@example.com");
        req.setPassword("wrongPassword");

        // 模拟数据库操作
        UserDO userDO = UserDO.builder()
                .id(1L)
                .passwordHashed("无法解析 'UserService' 中的方法 'hashPassword'")
                .build();
        when(userMapper.selectOne(any())).thenReturn(userDO);

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.login(req);
        });

        // 验证异常信息
        assertEquals("密码错误", exception.getMessage());

        // 验证数据库操作
        verify(userMapper, times(1)).selectOne(any());
    }

    @Test
    void testUpdatePassword_正常情况() {
        // 准备测试数据
        Long userId = 1L;
        String newPassword = "newPassword123";
        String confirmPassword = "newPassword123";

        // 模拟数据库操作
        UserDO userDO = UserDO.builder().id(userId).build();
        when(userMapper.selectById(userId)).thenReturn(userDO);
        when(userMapper.updateById(userDO)).thenReturn(1);

        // 执行被测方法
        assertDoesNotThrow(() -> {
            userService.updatePassword(userId, newPassword, confirmPassword);
        });

        // 验证数据库操作
        verify(userMapper, times(1)).selectById(userId);
        verify(userMapper, times(1)).updateById(userDO);
    }

    @Test
    void testUpdatePassword_新密码和确认密码不一致() {
        // 准备测试数据
        Long userId = 1L;
        String newPassword = "newPassword123";
        String confirmPassword = "differentPassword";

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.updatePassword(userId, newPassword, confirmPassword);
        });

        // 验证异常信息
        assertEquals("新密码和确认密码不一致", exception.getMessage());

        // 验证数据库操作没有发生
        verify(userMapper, never()).selectById(any());
        verify(userMapper, never()).updateById(any());
    }

    @Test
    void testUpdatePassword_用户不存在() {
        // 准备测试数据
        Long userId = 1L;
        String newPassword = "newPassword123";
        String confirmPassword = "newPassword123";

        // 模拟数据库操作
        when(userMapper.selectById(userId)).thenReturn(null);

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.updatePassword(userId, newPassword, confirmPassword);
        });

        // 验证异常信息
        assertEquals("用户不存在", exception.getMessage());

        // 验证数据库操作
        verify(userMapper, times(1)).selectById(userId);
        verify(userMapper, never()).updateById(any());
    }

    @Test
    void testUpdatePassword_更新失败() {
        // 准备测试数据
        Long userId = 1L;
        String newPassword = "newPassword123";
        String confirmPassword = "newPassword123";

        // 模拟数据库操作
        UserDO userDO = UserDO.builder().id(userId).build();
        when(userMapper.selectById(userId)).thenReturn(userDO);
        when(userMapper.updateById(userDO)).thenReturn(0);

        // 预期结果
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.updatePassword(userId, newPassword, confirmPassword);
        });

        // 验证异常信息
        assertEquals("密码更新失败", exception.getMessage());

        // 验证数据库操作
        verify(userMapper, times(1)).selectById(userId);
        verify(userMapper, times(1)).updateById(userDO);
    }
    @Mock
    private SessionStore sessionStore;
    @Test
    void testLogout_正常情况() {
        // 准备测试数据
        LogoutReq req = new LogoutReq();
        req.setUserId(1L);
        String token = UUID.randomUUID().toString();
        req.setToken(token);

        // 模拟会话存储操作

        when(sessionStore.getToken(req.getUserId())).thenReturn(token);
        doNothing().when(sessionStore).removeSession(req.getUserId());

        // 执行被测方法
        LogoutResp resp = userService.logout(req);

        // 验证结果
        assertNotNull(resp);
        assertTrue(resp.getSuccess());

        // 验证会话存储操作
        verify(sessionStore, times(1)).getToken(req.getUserId());
        verify(sessionStore, times(1)).removeSession(req.getUserId());
    }

    @Test
    void testLogout_无效令牌() {
        // 准备测试数据
        LogoutReq req = new LogoutReq();
        req.setUserId(1L);
        String token = UUID.randomUUID().toString();
        req.setToken(token);

        // 模拟会话存储操作
        when(sessionStore.getToken(req.getUserId())).thenReturn("differentToken");

        // 执行被测方法并捕获异常
        ClientException exception = assertThrows(ClientException.class, () -> {
            userService.logout(req);
        });

        // 验证异常信息
        assertEquals("无效的会话令牌", exception.getMessage());

        // 验证会话存储操作
        verify(sessionStore, times(1)).getToken(req.getUserId());
        verify(sessionStore, times(0)).removeSession(req.getUserId());
    }
}
