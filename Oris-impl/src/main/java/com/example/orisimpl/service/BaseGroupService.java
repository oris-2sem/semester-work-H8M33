package com.example.orisimpl.service;

import com.example.orisimpl.exception.GroupNotFoundException;
import com.example.orisimpl.model.AbstractEntity;
import com.example.orisimpl.model.GroupEntity;
import com.example.orisimpl.model.UserEntity;
import com.example.orisimpl.repository.GroupRepository;
import com.example.orisimpl.util.mapper.GroupMapper;
import dto.request.ChatRequest;
import dto.request.GroupRequest;
import dto.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseGroupService implements GroupService {

    private final GroupRepository repository;

    private final UserService userService;

    private final GroupMapper mapper;

    private final ChatService chatService;

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<GroupResponse> getAllGroups() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isUser(#groupId, authentication.principal)")
    public GroupResponse getGroupById(UUID groupId) {
        return repository.findById(groupId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isUser(#groupId, authentication.principal)")
    public void addUserToGroup(UUID groupId, UUID userId) {
        GroupEntity entity = getGroupEntity(groupId);
        entity.getUsers().add(userService.getUserEntity(userId));
        repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void removeUserFromGroup(UUID groupId, UUID userId) {
        GroupEntity entity = getGroupEntity(groupId);
        UserEntity user = userService.getUserEntity(userId);
        if (entity.getUsers().remove(user)) {
            repository.save(entity);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or #userId.equals(authentication.principal) ")
    public void createGroup(UUID userId, GroupRequest request) {
        GroupEntity entity = mapper.toEntity(request);
        UserEntity user = userService.getUserEntity(userId);
        HashSet<UserEntity> users = new HashSet<>();
        HashSet<UserEntity> admins = new HashSet<>();
        entity.setChat(chatService.createChat(userId, new ChatRequest(request.getTitle())));
        users.add(user);
        admins.add(user);
        entity.setUsers(users);
        entity.setAdmins(admins);
        repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void addAdminToGroup(UUID groupId, UUID userId) {
        GroupEntity entity = getGroupEntity(groupId);
        UserEntity user = userService.getUserEntity(userId);
        if (entity.getUsers().contains(user)) {
            entity.getAdmins().add(user);
            repository.save(entity);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void removeAdminFromGroup(UUID groupId, UUID userId) {
        GroupEntity entity = getGroupEntity(groupId);
        UserEntity user = userService.getUserEntity(userId);
        if (entity.getAdmins().remove(user)) {
            repository.save(entity);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void delete(UUID groupId) {
        repository.deleteById(groupId);
    }

    @Override
    public GroupEntity getGroupEntity(UUID groupID) {
        return repository.findById(groupID).orElseThrow(() -> new GroupNotFoundException(groupID));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void updateGroup(UUID groupId, GroupRequest request) {
        GroupEntity entityToUpdate = getGroupEntity(groupId);
        GroupEntity entity = mapper.toEntity(request);
        entity.setChat(entityToUpdate.getChat());
        entity.setPosts(entityToUpdate.getPosts());
        entity.setAdmins(entityToUpdate.getAdmins());
        entity.setUsers(entityToUpdate.getUsers());
        repository.save(entity);
    }

    @Override
    public List<GroupResponse> getGroupsByUserId(UUID userId) {
        return repository.findDistinctByUsers_id(userId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public boolean isAdmin(UUID groupId, UUID userId){
        return repository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId))
                .getAdmins().stream().map(AbstractEntity::getId).toList().contains(userId);
    }

    public boolean isUser(UUID groupId, UUID userId){
        return repository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId))
                .getUsers().stream().map(AbstractEntity::getId).toList().contains(userId);
    }
}

