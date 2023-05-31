package com.example.orisimpl.service;

import com.example.orisimpl.exception.UserNotFoundException;
import com.example.orisimpl.model.UserEntity;
import com.example.orisimpl.model.enums.Status;
import com.example.orisimpl.repository.UserRepository;
import com.example.orisimpl.security.enums.UserRole;
import com.example.orisimpl.security.model.RoleEntity;
import com.example.orisimpl.security.service.RoleService;
import com.example.orisimpl.util.mapper.UserMapper;
import dto.request.UserRequest;
import dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final EmailService emailService;

    @Override
    public void createUser(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity userEntity = mapper.toEntity(request);
        userEntity.setRoles(Collections.singleton(roleService.findRole(UserRole.USER).orElse(getRole(UserRole.USER))));
        userEntity.setStatus(Status.ACTIVATION_WAITING);
        repository.save(userEntity);
        emailService.sendEmail(request.getEmail(),"Registration", String.format("Congratulation! You had been registered on our site!" +
                "You can activate your account with this code: %s", userEntity.getId()));
    }

    @Override
    @PreAuthorize("#userId.equals(authentication.principal) or hasAuthority('ADMIN')")
    public UserResponse updateUser(UUID userId, UserRequest request) {
        Optional<UserEntity> userEntityOptional = repository.findById(userId);
        if (userEntityOptional.isPresent()){
            UserEntity userEntity = mapper.toEntity(request);
            userEntity.setId(userId);
            userEntity.setChats(userEntityOptional.get().getChats());
            return mapper.toResponse(repository.save(userEntity));
        }
        throw new UserNotFoundException(userId);
    }

    @Override
    @PreAuthorize("#userId.equals(authentication.principal) or hasAuthority('ADMIN')")
    public void deleteUserById(UUID userId) {
        repository.deleteById(userId);
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        return repository.findById(userId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getUsers() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public UserEntity getUserEntity(UUID userId) {
        Optional<UserEntity> userEntityOptional = repository.findById(userId);
        if (userEntityOptional.isPresent()){
            return userEntityOptional.get();
        }
        throw new UserNotFoundException(userId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseChatService.isUser(#chatID, authentication.principal)")
    public List<UserResponse> getUsersByChatId(UUID chatID) {
        return repository.getUsersByChats_Id(chatID).stream().map(mapper::toResponse).toList();
    }

    @Override
    public UserResponse getUserByPostId(UUID postId) {
        return mapper.toResponse(repository.getUserByPosts_Id(postId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isUser(#groupId, authentication.principal)")
    public List<UserResponse> getUserByGroupId(UUID groupId) {
        return repository.getUsersByGroups_Id(groupId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isUser(#groupId, authentication.principal)")
    public List<UserResponse> getAdminsByGroupId(UUID groupId) {
        return repository.getUsersByGroupAdmin_Id(groupId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void setAdminRole(UUID userId) {
        UserEntity entity = getUserEntity(userId);
        entity.getRoles().add(roleService.findRole(UserRole.ADMIN).orElse(getRole(UserRole.ADMIN)));
        repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeAdminRole(UUID userId) {
        UserEntity entity = getUserEntity(userId);
        entity.getRoles().remove(roleService.findRole(UserRole.ADMIN).orElse(getRole(UserRole.ADMIN)));
        repository.save(entity);
    }

    @Override
    public UserEntity getUserEntityByUsername(String username) {
        return repository.getUserByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseUserService.isActivationWaiting(#userId)")
    public void activateUser(UUID userId) {
        UserEntity entity = getUserEntity(userId);
        entity.setStatus(Status.ACTIVATED);
        repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void banUser(UUID userId) {
        UserEntity entity = getUserEntity(userId);
        entity.setStatus(Status.BANNED);
        repository.save(entity);
    }

    private RoleEntity getRole(UserRole role) {
        return RoleEntity.builder()
                .role(role)
                .build();
    }

    public boolean isActivationWaiting(UUID userId){
        return Status.ACTIVATION_WAITING.equals(getUserEntity(userId).getStatus());
    }

    public boolean isActivated(String username){
        return Status.ACTIVATED.equals(getUserEntityByUsername(username).getStatus());
    }
}
