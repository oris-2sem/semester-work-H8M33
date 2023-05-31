package com.example.orisimpl.security.exception;

import com.example.orisimpl.exception.NotFoundException;

import java.util.UUID;

public class RefreshTokenNotFoundException extends NotFoundException {
    public RefreshTokenNotFoundException(UUID userID) {
        super(String.format("User with this id = %s, not authorized", userID));
    }
}
