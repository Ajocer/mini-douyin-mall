package org.miniTT.mall.user.controller;

import lombok.AllArgsConstructor;
import org.miniTT.mall.common.convention.result.Result;
import org.miniTT.mall.common.convention.result.Results;
import org.miniTT.mall.user.dto.req.RegisterReq;
import org.miniTT.mall.user.dto.resp.RegisterResp;
import org.miniTT.mall.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户注册
     * @param req
     * @return
     */
    @PostMapping("/api/miniTT/user/v1/register")
    public Result<RegisterResp> register(@RequestBody RegisterReq req) {
        return Results.success(userService.register(req));
    }
}
