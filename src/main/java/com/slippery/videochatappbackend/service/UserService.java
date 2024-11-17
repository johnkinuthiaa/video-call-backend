package com.slippery.videochatappbackend.service;

import com.slippery.videochatappbackend.dto.UserDto;
import com.slippery.videochatappbackend.models.User;

public interface UserService {
    UserDto logIn(User user);
    UserDto register(User user);
    UserDto findAllUsers();


}
