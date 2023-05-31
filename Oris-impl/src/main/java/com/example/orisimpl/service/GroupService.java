package com.example.orisimpl.service;

import com.example.orisimpl.model.GroupEntity;
import dto.request.GroupRequest;
import dto.response.GroupResponse;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupResponse> getAllGroups();

    GroupResponse getGroupById(UUID groupId);

    void addUserToGroup(UUID groupId, UUID userId);

    void removeUserFromGroup(UUID groupId, UUID userId);

    void createGroup(UUID userId, GroupRequest request);

    void addAdminToGroup(UUID groupId, UUID userId);

    void removeAdminFromGroup(UUID groupId, UUID userId);

    void delete(UUID groupId);

    GroupEntity getGroupEntity(UUID groupID);

    void updateGroup(UUID groupId, GroupRequest request);

    List<GroupResponse> getGroupsByUserId(UUID userId);
}
