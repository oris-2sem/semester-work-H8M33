package com.example.orisimpl.security.controller;

import api.AuthApi;
import com.example.orisimpl.security.service.AuthService;
import dto.request.AuthRequest;
import dto.request.RefreshJwtRequest;
import dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @Override
    public AuthResponse getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @Override
    public AuthResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        return authService.refresh(request.getRefreshToken());
    }

    @Override
    public void logout() {
        authService.logout();
    }

}
