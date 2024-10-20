package com.hitweb.interceptor;

import com.hitweb.message.ThreadCurrent;
import com.hitweb.utils.JwtProperties;
import com.hitweb.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.hitweb.message.Message.USER_ID;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtTokenInterceptor implements HandlerInterceptor {
    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token  = request.getHeader(jwtProperties.getTokenName());
        try {
            log.info("token:{}",token);
            Claims claims = JwtUtils.parseJWT(jwtProperties.getSecret(), token);
            log.info("claims:{}",claims);
            Long userId = Long.valueOf(claims.get(USER_ID).toString());
            log.info("userId:{}",userId);
            ThreadCurrent.setCurrentId(userId);
            return true;
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 使用标准的 HTTP 状态码
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"code\":0,\"msg\":\"NOT_LOGIN\"}"); // 返回 JSON 格式的响应
            return false;
        }
    }
}
