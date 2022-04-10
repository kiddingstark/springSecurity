package com.springsecurity.security;

import com.springsecurity.dto.UserEntity;
import com.springsecurity.service.impl.UserDetailServiceImpl;
import com.springsecurity.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO
 * @Author Ricky
 * @Date 2022/2/25 0:32
 */

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private JwtManager jwtManager;

    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 问题：每一次调用接口都要经过此过滤器，每一次解析玩都要调用loadUserByUsername查询数据库？
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头中获取token字符串并解析（JwtManager之前文章有详解，这里不多说了）
        Claims claims = jwtManager.parse(request.getHeader("Authorization"));
        if (claims != null) {
            // 从`JWT`中提取出之前存储好的用户名
            String username = claims.getSubject();
            /**
             * 可以直接判断username是否不为空，不调用以下代码把用户信息放到上下文中，毕竟JWT方式就是无状态的，只要你解析JWT成功则认为你已登录
             * 若是需要在上下文中获取用户信息，则可以封装一个方法在上下文中获取该JWT解析
             */

            System.out.println(SessionHelper.getCurrentUser(jwtManager));
            // 查询出用户对象
            UserDetails user = userDetailsService.loadUserByUsername(username);
            // 手动组装一个认证对象
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            // 将认证对象放到上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * ration中的鉴权流程总结：通过auth2（结合SpringSecurity认证通过，具体如何结合？）颁发一个tonken返回，前端请求每次都携带无状态的token
     * 在gateway服务中创建一个拦截器继承GlobalFilter解析token获取用户信息，解析成功则放行
     */

}
