package com.slippery.videochatappbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.videochatappbackend.models.User;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String message;
    private int statusCode;
    private String error;
    private String username;
    private String email;
    private String password;
    private String status;
    private String role;
    private String jwtToken;
    private User user;
    private List<User> userList;

}
