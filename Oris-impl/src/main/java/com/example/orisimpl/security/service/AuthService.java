package com.example.orisimpl.security.service;

import dto.request.AuthRequest;
import dto.response.AuthResponse;
import lombok.NonNull;

public interface AuthService {
    AuthResponse login(@NonNull AuthRequest authRequest);

    AuthResponse getAccessToken(@NonNull String refreshToken);

    AuthResponse refresh(@NonNull String refreshToken);

    void logout();
}
