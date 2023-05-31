package com.example.orisimpl.service;


import com.example.orisimpl.model.UserEntity;
import dto.request.UserRequest;
import dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserRequest request);

    UserResponse updateUser(UUID userId, UserRequest request);

    void deleteUserById(UUID userId);

    UserResponse getUserById(UUID userId);

    List<UserResponse> getUsers();

    UserEntity getUserEntity(UUID userid);

    List<UserResponse> getUsersByChatId(UUID chatID);

    UserResponse getUserByPostId(UUID postId);

    List<UserResponse> getUserByGroupId(UUID groupId);

    List<UserResponse> getAdminsByGroupId(UUID groupId);

    void setAdminRole(UUID userId);

    void removeAdminRole(UUID userId);

    UserEntity getUserEntityByUsername(String username);

    void activateUser(UUID userId);

    void banUser(UUID userId);
}
