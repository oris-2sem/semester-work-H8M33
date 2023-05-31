package com.example.orisimpl.controllers.rest;

import api.UserApi;
import com.example.orisimpl.service.UserService;
import dto.request.UserRequest;
import dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;

    @Override
    public void activateUser(UUID userId) {
        service.activateUser(userId);
    }

    @Override
    public void banUser(UUID userId) {
        service.banUser(userId);
    }

    @Override
    public void createUser(UserRequest request) {
        service.createUser(request);
    }

    @Override
    public UserResponse updateUser(UUID userId, UserRequest request) {
        return service.updateUser(userId, request);
    }

    @Override
    public void deleteUserById(UUID userId) {
        service.deleteUserById(userId);
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        return service.getUserById(userId);
    }

    @Override
    public List<UserResponse> getUsers() {
        return service.getUsers();
    }

    @Override
    public List<UserResponse> getUsersByChatId(UUID chatID) {
        return service.getUsersByChatId(chatID);
    }

    @Override
    public UserResponse getUserByPostId(UUID postId) {
        return service.getUserByPostId(postId);
    }

    @Override
    public List<UserResponse> getUsersByGroupId(UUID groupId) {
        return service.getUserByGroupId(groupId);
    }

    @Override
    public List<UserResponse> getAdminsByGroupId(UUID groupId) {
        return service.getAdminsByGroupId(groupId);
    }

    @Override
    public void setAdminRole(UUID userId) {
        service.setAdminRole(userId);
    }

    @Override
    public void setUserRole(UUID userId) {
        service.removeAdminRole(userId);
    }
}
