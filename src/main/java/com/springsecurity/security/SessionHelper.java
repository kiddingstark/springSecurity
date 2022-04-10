package com.springsecurity.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author Ricky
 * @Date 2022/2/25 19:13
 */
public class SessionHelper {

    public static String getCurrentUser(JwtManager jwtManager) {
        // 通过request对象获取session对象，再获取当前用户对象
        Claims claims = jwtManager.parse(getCurrentRequest().getHeader("Authorization"));
        if (claims != null) {
            // 从`JWT`中提取出之前存储好的用户名
            return claims.getSubject();
        }
        return null;
    }

    public static HttpServletRequest getCurrentRequest() {
        // 获取当前request请求对象
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }
}
