package api;

import dto.request.AuthRequest;
import dto.request.RefreshJwtRequest;
import dto.response.AuthResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "Authentication | Аутентификация", value = "Аутентификация")
@RequestMapping("/auth")
public interface AuthApi {

    @PostMapping("/login")
    AuthResponse login(@RequestBody AuthRequest authRequest);

    @PostMapping("/token")
    AuthResponse getNewAccessToken(@RequestBody RefreshJwtRequest request);

    @PostMapping("/refresh")
    AuthResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request);

    @PostMapping("/logout")
    void logout();
}
