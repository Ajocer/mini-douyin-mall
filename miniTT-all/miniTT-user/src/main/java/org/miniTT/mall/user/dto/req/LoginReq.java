package org.miniTT.mall.user.dto.req;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
