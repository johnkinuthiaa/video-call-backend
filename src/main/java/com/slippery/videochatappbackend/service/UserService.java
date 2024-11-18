package com.slippery.videochatappbackend.service;

import com.slippery.videochatappbackend.dto.UserDto;
import com.slippery.videochatappbackend.models.User;

import java.util.List;

public interface UserService {
    UserDto logIn(User user);
    UserDto register(User user);
    UserDto findAllUsers();
    UserDto findAllUsernames();


}
