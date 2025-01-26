//package org.miniTT.mall.gateway.config;
//
//import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.router.SaRouter;
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.List;
//@Configuration
//public class SatokenConfiguration implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SaInterceptor(handle -> {
//            SaRouter
//                    .match("/**")
//                    .notMatch(excludePaths())
//                    .check(r -> StpUtil.checkLogin());
//        })).addPathPatterns("/**");
//    }
//
//    // 动态获取哪些 path 可以忽略鉴权
//    public List<String> excludePaths() {
//        // 此处仅为示例，实际项目你可以写任意代码来查询这些path
//        return Arrays.asList("/path1", "/path2", "/api/miniTT/user/v1/register","/api/miniTT/user/v1/login");
//    }
//
//}
