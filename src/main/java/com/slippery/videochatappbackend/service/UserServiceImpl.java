package com.slippery.videochatappbackend.service;

import com.slippery.videochatappbackend.dto.UserDto;
import com.slippery.videochatappbackend.models.User;
import com.slippery.videochatappbackend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final JwtService service;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository repository, JwtService service, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto logIn(User user) {
        UserDto response =new UserDto();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()));
        if (authentication.isAuthenticated()) {
            user.setStatus("active");
            response.setStatusCode(200);
            response.setMessage("user logged in successfully");
            response.setJwtToken(service.generateJwtToken(user.getUsername()));
            response.setUser(user);

        }else{
            response.setMessage("user not logged in successfully");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public UserDto register(User user) {
        UserDto response =new UserDto();
        User existingUsername =repository.findByUsername(user.getUsername());
        User existingUserEmail =repository.findByEmail(user.getEmail());
        if(existingUsername ==null && existingUserEmail ==null){
            response.setStatus("active");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            response.setMessage("user "+user.getUsername()+" registered successfully");
            response.setStatusCode(200);
            response.setUser(user);
        }else{
            response.setStatusCode(401);
            response.setError("user with either username "+user.getUsername()+" or email"+user.getEmail()+" already exists. please check your email or username");
        }
        return response;
    }

    @Override
    public UserDto findAllUsers() {
        UserDto response =new UserDto();
        var users= repository.findAll();
        if(!users.isEmpty()){
            response.setMessage("all users");
            response.setStatusCode(200);
            response.setUserList(users);
        }else{
            response.setStatusCode(204);
            response.setMessage("user list is empty!");
        }
        return response;
    }
}
