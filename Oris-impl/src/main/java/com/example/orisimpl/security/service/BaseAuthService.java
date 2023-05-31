package com.example.orisimpl.security.service;

import com.example.orisimpl.exception.UserNotFoundException;
import com.example.orisimpl.model.UserEntity;
import com.example.orisimpl.security.exception.AuthException;
import com.example.orisimpl.security.exception.RefreshTokenNotFoundException;
import com.example.orisimpl.security.model.RefreshTokenEntity;
import com.example.orisimpl.security.repository.RefreshTokenRepository;
import com.example.orisimpl.service.UserService;
import dto.request.AuthRequest;
import dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseAuthService implements AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder encoder;

    private final AuthInfoService authInfoService;

    private final RefreshTokenRepository repository;

    @Override
    @PreAuthorize("@baseUserService.isActivated(#authRequest.username)")
    @Transactional
    public AuthResponse login(@NonNull AuthRequest authRequest) {
        final UserEntity user = userService.getUserEntityByUsername(authRequest.getUsername());
        if (encoder.matches(authRequest.getPassword(), user.getHashPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            final RefreshTokenEntity entity = RefreshTokenEntity.builder().user(user).refreshToken(refreshToken).build();
            repository.saveAndUpdate(UUID.randomUUID(),entity.getUser().getId(), entity.getRefreshToken());
            return new AuthResponse(accessToken, refreshToken);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public AuthResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final UUID userId = UUID.fromString(claims
                    .get("id", String.class));
            final String saveRefreshToken = repository.findByUserId(userId).
                    orElseThrow(() -> new RefreshTokenNotFoundException(userId)).getRefreshToken();
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.getUserEntityByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new AuthResponse(accessToken, null);
            }
        }
        return new AuthResponse(null, null);
    }

    @Override
    public AuthResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final UUID userId = UUID.fromString(claims
                    .get("id", String.class));
            final String saveRefreshToken = repository.findByUserId(userId).
                    orElseThrow(() -> new RefreshTokenNotFoundException(userId)).getRefreshToken();
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.getUserEntityByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                final RefreshTokenEntity entity = RefreshTokenEntity.builder().user(user).refreshToken(refreshToken).build();
                repository.saveAndUpdate(UUID.randomUUID(),entity.getUser().getId(), entity.getRefreshToken());
                return new AuthResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    @Override
    @Transactional
    public void logout() {
        repository.deleteByUserId(authInfoService.getAuthInfo().getId());
    }

}
