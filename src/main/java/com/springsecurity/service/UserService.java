package com.springsecurity.service;

import com.springsecurity.dto.LoginDto;
import com.springsecurity.dto.UserEntity;

public interface UserService {

    UserEntity login(LoginDto loginDto);
}
