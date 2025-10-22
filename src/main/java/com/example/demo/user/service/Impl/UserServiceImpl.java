package com.example.demo.user.service.Impl;

import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.UserResponse;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        var savedUser = mapper.toUser(userRequest);
        User newUser = userRepository.save(savedUser);
        log.info("User created successfully");
        return mapper.toUserResponse(newUser);
    }
}
