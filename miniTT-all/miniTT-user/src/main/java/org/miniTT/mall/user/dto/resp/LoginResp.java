package org.miniTT.mall.user.dto.resp;

import lombok.Data;

@Data
public class LoginResp {
    /**
     * 用户id
     */
    private Long user_id;

    /**
     * token
     */
    private String token;

}
