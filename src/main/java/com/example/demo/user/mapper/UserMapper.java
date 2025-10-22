package com.example.demo.user.mapper;

import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.UserResponse;
import com.example.demo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);

    User updateUser(@MappingTarget User user, UserRequest userRequest);
}
