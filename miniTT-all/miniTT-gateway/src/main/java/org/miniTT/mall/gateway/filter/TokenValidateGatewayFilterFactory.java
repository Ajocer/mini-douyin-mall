package org.miniTT.mall.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.miniTT.mall.gateway.config.Config;
import org.miniTT.mall.gateway.dto.GatewayErrorResult;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * SpringCloud Gateway Token 拦截器
 */
@Component
public class TokenValidateGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    private final StringRedisTemplate stringRedisTemplate;

    public TokenValidateGatewayFilterFactory(StringRedisTemplate stringRedisTemplate) {
        super(Config.class);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String username = request.getHeaders().getFirst("username");
            String token = request.getHeaders().getFirst("token");
            Object userInfo;
            if (StringUtils.hasText(username) && StringUtils.hasText(token) && (userInfo = stringRedisTemplate.opsForHash().get("miniTT:login:" + username, token)) != null) {
                JSONObject userInfoJsonObject = JSON.parseObject(userInfo.toString());
                ServerHttpRequest.Builder builder = exchange.getRequest().mutate().headers(httpHeaders -> {
                    httpHeaders.set("userId", userInfoJsonObject.getString("id"));
                    try {
                        httpHeaders.set("realName", URLEncoder.encode(userInfoJsonObject.getString("realName"), "UTF-8")); // 修改此行
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                });
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }

            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.fromSupplier(() -> {
                DataBufferFactory bufferFactory = response.bufferFactory();
                GatewayErrorResult resultMessage = GatewayErrorResult.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("Token validation error")
                        .build();
                return bufferFactory.wrap(JSON.toJSONString(resultMessage).getBytes());
            }));
        };
    }
}
