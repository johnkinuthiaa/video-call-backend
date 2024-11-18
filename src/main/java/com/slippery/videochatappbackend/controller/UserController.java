package com.slippery.videochatappbackend.controller;

import com.slippery.videochatappbackend.dto.UserDto;
import com.slippery.videochatappbackend.models.User;
import com.slippery.videochatappbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video-app")
//@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/active-users")
    public ResponseEntity<UserDto> findAllUsernames(){
        return ResponseEntity.ok(userService.findAllUsernames());
    }
    @GetMapping("/all-users")
    public ResponseEntity<UserDto> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User user){
        return ResponseEntity.ok(userService.logIn(user));
    }
}
