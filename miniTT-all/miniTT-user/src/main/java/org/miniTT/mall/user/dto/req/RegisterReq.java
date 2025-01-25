package org.miniTT.mall.user.dto.req;

import lombok.Data;

@Data
public class RegisterReq {
    /**
     * 邮箱
     */
        private String email;

        /**
         * 密码
         */
        private String password;

        /**
         * 确认密码
         */
        private String confirm_password;
}
