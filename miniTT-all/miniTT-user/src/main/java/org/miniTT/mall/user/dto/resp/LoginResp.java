package org.miniTT.mall.user.dto.resp;

import lombok.Data;

@Data
public class LoginResp {
    private Long user_id;
    private String token;

    public void setToken(String token) {
        this.token = token;
    }
}
