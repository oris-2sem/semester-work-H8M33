package com.example.orisimpl.service;

import dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageService {
    void uploadImage(MultipartFile file, UUID usedId) throws IOException;

    byte[] getImage(UUID imageId);

    void signeeToUser(UUID imageId, UUID userId);

    void signeeToGroup(UUID imageId, UUID groupId);

    void deleteById(UUID imageId);

    void unsigneeFromUser(UUID imageId, UUID userId);

    void unsigneeFromGroup(UUID imageId, UUID groupId);

    List<ImageResponse> getUserImages(UUID userId);

    List<ImageResponse> getGroupImages(UUID groupId);
}
