package com.example.orisimpl.controllers.rest;

import api.ImageApi;
import com.example.orisimpl.service.ImageService;
import dto.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ImageController implements ImageApi {

    private final ImageService imageService;

    @Override
    public void uploadImage(MultipartFile file, UUID userId) throws IOException {
        imageService.uploadImage(file, userId);
    }

    @Override
    public ResponseEntity<?> getImageById(@PathVariable UUID imageId) {
        byte[] image = imageService.getImage(imageId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @Override
    public void signeeToUser(UUID imageId, UUID userId) {
        imageService.signeeToUser(imageId, userId);
    }

    @Override
    public void signeeToGroup(UUID imageId, UUID groupId) {
        imageService.signeeToGroup(imageId, groupId);
    }

    @Override
    public void deleteImageById(UUID imageId) {
        imageService.deleteById(imageId);
    }

    @Override
    public void unsigneeFromUser(UUID imageId, UUID userId) {
        imageService.unsigneeFromUser(imageId, userId);
    }

    @Override
    public void unsigneeFromGroup(UUID imageId, UUID groupId) {
        imageService.unsigneeFromGroup(imageId, groupId);
    }

    @Override
    public List<ImageResponse> getUserImages(UUID userId) {
        return imageService.getUserImages(userId);
    }

    @Override
    public List<ImageResponse> getGroupImages(UUID groupId) {
        return imageService.getGroupImages(groupId);
    }


}