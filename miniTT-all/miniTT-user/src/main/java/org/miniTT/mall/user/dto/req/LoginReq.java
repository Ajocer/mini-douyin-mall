package org.miniTT.mall.user.dto.req;

import lombok.Data;

@Data
public class LoginReq {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

}
