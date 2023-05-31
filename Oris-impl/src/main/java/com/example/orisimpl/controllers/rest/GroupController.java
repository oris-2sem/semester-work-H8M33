package com.example.orisimpl.controllers.rest;

import api.GroupApi;
import com.example.orisimpl.service.GroupService;
import dto.request.GroupRequest;
import dto.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GroupController implements GroupApi {

    private final GroupService groupService;

    @Override
    public List<GroupResponse> getGroups() {
        return groupService.getAllGroups();
    }

    @Override
    public GroupResponse getGroup(UUID groupId) {
        return groupService.getGroupById(groupId);
    }

    @Override
    public List<GroupResponse> getGroupsByUserId(UUID userId) {
        return groupService.getGroupsByUserId(userId);
    }

    @Override
    public void addUserToGroup(UUID groupId, UUID userId) {
        groupService.addUserToGroup(groupId, userId);
    }

    @Override
    public void removeUserFromGroup(UUID groupId, UUID userId) {
        groupService.removeUserFromGroup(groupId, userId);
    }

    @Override
    public void createGroup(UUID userId, GroupRequest request) {
        groupService.createGroup(userId, request);
    }

    @Override
    public void addAdminToGroup(UUID groupId, UUID userId) {
        groupService.addAdminToGroup(groupId, userId);
    }

    @Override
    public void removeAdminFromGroup(UUID groupId, UUID userId) {
        groupService.removeAdminFromGroup(groupId, userId);
    }

    @Override
    public void deleteGroup(UUID groupId) {
        groupService.delete(groupId);
    }

    @Override
    public void updateGroup(UUID groupId, GroupRequest request) {
        groupService.updateGroup(groupId, request);
    }
}
