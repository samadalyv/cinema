package com.example.demo.user.service;


import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
}
