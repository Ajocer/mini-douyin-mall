package org.miniTT.mall.user.dto.req;

import lombok.Data;

@Data
public class UpdatePasswordReq {
    private Long userId;
    private String newPassword;
    private String confirmPassword;


}