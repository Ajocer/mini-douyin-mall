package org.miniTT.mall.user.dto.req;
import lombok.Data;

@Data
public class LogoutReq {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * token
     */
    private String token;

}
