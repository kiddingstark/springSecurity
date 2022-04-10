package com.springsecurity.controller;

import com.springsecurity.dto.LoginDto;
import com.springsecurity.dto.UserEntity;
import com.springsecurity.service.UserService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author Ricky
 * @Date 2022/2/24 18:02
 */
@Slf4j
@RequestMapping("/API")
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * session方式的login方法
     *
     * @param loginDto
     * @return
     */
    @PostMapping("/loginBySession")
    public String login(@RequestBody LoginDto loginDto) {
        // 生成一个包含账号密码的认证信息
        Authentication token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        // AuthenticationManager校验这个认证信息，返回一个已认证的Authentication
        Authentication authentication = authenticationManager.authenticate(token);
        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "登录成功";
    }

    /**
     * JWT方式的login方法
     *
     * @param loginDto
     * @return
     */
    @PostMapping("/loginByJwt")
    public UserEntity loginByJwt(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping("/test")
    public String test() {
        log.info("---test---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString());
        return "认证通过";
    }
}
