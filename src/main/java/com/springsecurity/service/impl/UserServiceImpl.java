package com.springsecurity.service.impl;

import com.springsecurity.dto.LoginDto;
import com.springsecurity.dto.UserEntity;
import com.springsecurity.security.JwtManager;
import com.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author Ricky
 * @Date 2022/2/25 0:15
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtManager jwtManager;

    @Override
    public UserEntity login(LoginDto loginDto) {
        //此处为了方便测试写死数据
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginDto.getUsername());
        userEntity.setToken(jwtManager.generate(loginDto.getUsername()));
        return userEntity;
    }
}
