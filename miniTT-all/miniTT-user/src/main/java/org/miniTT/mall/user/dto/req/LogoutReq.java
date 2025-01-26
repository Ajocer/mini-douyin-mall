package org.miniTT.mall.user.dto.req;
import lombok.Data;

@Data
public class LogoutReq {
    private Long userId;
    private String token;
}
