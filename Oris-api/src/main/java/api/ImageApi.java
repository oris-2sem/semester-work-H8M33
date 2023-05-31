package api;

import dto.response.ImageResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags = "Images | Изображения", value = "Изображение")
@RequestMapping("/images")
public interface ImageApi {
    @PostMapping("/{userId}")
    void uploadImage(@RequestParam("image") MultipartFile file, @PathVariable UUID userId) throws IOException;

    @GetMapping("/png/{imageId}")
    ResponseEntity<?> getImageById(@PathVariable UUID imageId);

    @PatchMapping("/{imageId}/user/{userId}")
    void signeeToUser(@PathVariable UUID imageId, @PathVariable UUID userId);

    @PatchMapping("/{imageId}/group/{groupId}")
    void signeeToGroup(@PathVariable UUID imageId, @PathVariable UUID groupId);

    @DeleteMapping("/{imageId}")
    void deleteImageById(@PathVariable UUID imageId);

    @DeleteMapping("/{imageId}/user/{userId}")
    void unsigneeFromUser(@PathVariable UUID imageId, @PathVariable UUID userId);

    @DeleteMapping("/{imageId}/group/{groupId}")
    void unsigneeFromGroup(@PathVariable UUID imageId, @PathVariable UUID groupId);

    @GetMapping("/user/{userId}")
    List<ImageResponse> getUserImages(@PathVariable UUID userId);

    @GetMapping("/group/{groupId}")
    List<ImageResponse> getGroupImages(@PathVariable UUID groupId);
}
