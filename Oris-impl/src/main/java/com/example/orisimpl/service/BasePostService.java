package com.example.orisimpl.service;

import com.example.orisimpl.exception.PostNotFoundException;
import com.example.orisimpl.model.PostEntity;
import com.example.orisimpl.model.UserEntity;
import com.example.orisimpl.repository.PostRepository;
import com.example.orisimpl.util.mapper.PostMapper;

import dto.request.PostRequest;
import dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasePostService implements PostService{

    private final PostRepository repository;
    private final PostMapper mapper;
    private final UserService userService;

    private final GroupService groupService;

    @Override
    @PreAuthorize("#userId.equals(authentication.principal)")
    public void createPost(UUID userId, PostRequest request) {
        PostEntity postEntity = mapper.toEntity(request);
        UserEntity entity = userService.getUserEntity(userId);
        postEntity.setUser(entity);
        repository.save(postEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or (#userId.equals(authentication.principal) and @baseGroupService.isAdmin(#groupId, authentication.principal))")
    public void createPost(UUID groupId, UUID userId, PostRequest request) {
        PostEntity postEntity = mapper.toEntity(request);
        postEntity.setUser(userService.getUserEntity(userId));
        postEntity.setGroup(groupService.getGroupEntity(groupId));
        repository.save(postEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or  @basePostService.isAuthor(authentication.principal, #postId)")
    public PostResponse updatePost(UUID postId, PostRequest request) {
        Optional<PostEntity> postEntityOptional = repository.findById(postId);
        if (postEntityOptional.isPresent()){
            PostEntity postEntity = mapper.toEntity(request);
            postEntity.setId(postId);
            return mapper.toResponse(repository.save(postEntity));
        }
        throw new PostNotFoundException(postId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or  @basePostService.isAuthor(authentication.principal, #postId)")
    public void deletePostById(UUID postId) {
        repository.deleteById(postId);
    }

    @Override
    public PostResponse getPostById(UUID postId) {
        return repository.findById(postId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public List<PostResponse> getPosts() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PostResponse> getPostsByUserId(UUID userId) {
        return repository.getPostsByUser_Id(userId).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PostResponse> getPostsByGroupId(UUID groupId) {
        return groupService.getGroupEntity(groupId).getPosts().stream().map(mapper::toResponse).toList();
    }

    public boolean isAuthor(UUID userId, UUID postId){
        return repository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId)).getUser().equals(userService.getUserEntity(userId));
    }
}
