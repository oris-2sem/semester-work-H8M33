package com.example.orisimpl.exception;

import java.util.UUID;

public class PostNotFoundException extends NotFoundException{
    public PostNotFoundException(UUID postId) {
        super(String.format("Post with this id = %s, not found", postId));
    }
}
