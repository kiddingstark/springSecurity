package com.springsecurity.service.impl;

import com.springsecurity.dto.UserDetailDto;
import com.springsecurity.dto.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @Description TODO
 * @Author Ricky
 * @Date 2022/2/24 18:00
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("$2a$10$3H9S6AnNlczr2KherCCaqetAR3AWDa3lqZwa77K3NUwk6/aeiZi1y");//123456加密后的字符串，此处为了测试方便写死数据
        return new UserDetailDto(userEntity, Collections.emptyList());
    }
}
