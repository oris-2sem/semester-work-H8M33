package com.example.orisimpl.service;

import dto.request.PostRequest;
import dto.response.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {

    void createPost(UUID userId, PostRequest request);

    void createPost(UUID groupId, UUID userId, PostRequest request);

    PostResponse updatePost(UUID postId, PostRequest request);

    void deletePostById(UUID postId);

    PostResponse getPostById(UUID postId);

    List<PostResponse> getPosts();

    List<PostResponse> getPostsByUserId(UUID userId);

    List<PostResponse> getPostsByGroupId(UUID groupId);
}
