package org.miniTT.mall.user.dto.req;

import lombok.Data;

@Data
public class UpdatePasswordReq {
    private String email;
    private String newPassword;
    private String confirmPassword;


}