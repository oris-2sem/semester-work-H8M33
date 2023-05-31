package com.example.orisimpl.service;

import com.example.orisimpl.exception.ImageNotFoundException;
import com.example.orisimpl.model.ImageEntity;
import com.example.orisimpl.model.UserEntity;
import com.example.orisimpl.repository.ImageRepository;
import com.example.orisimpl.util.ImageUtil;
import com.example.orisimpl.util.mapper.ImageMapper;
import dto.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseImageService implements ImageService {

    private final ImageRepository imageRepository;

    private final UserService userService;

    private final GroupService groupService;

    private final ImageMapper mapper;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or #userId.equals(authentication.principal)")
    public void uploadImage(MultipartFile file, UUID userId) throws IOException {
        UserEntity entity = userService.getUserEntity(userId);
        Set<UserEntity> set = new HashSet<>();
        set.add(entity);
        imageRepository.save(ImageEntity.builder()
                .type(file.getContentType())
                        .author(entity)
                        .ownersUsers(set)
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

    }

    @Transactional
    @Override
    public byte[] getImage(UUID imageId) {
        ImageEntity entity = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        return ImageUtil.decompressImage(entity.getImageData());
    }

    @Override
    @PreAuthorize("#userId.equals(authentication.principal) or hasAuthority('ADMIN')")
    public void signeeToUser(UUID imageId, UUID userId) {
        ImageEntity imageEntity = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        imageEntity.getOwnersUsers().add(userService.getUserEntity(userId));
        imageRepository.save(imageEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void signeeToGroup(UUID imageId, UUID groupId) {
        ImageEntity imageEntity = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        imageEntity.getOwnersGroups().add(groupService.getGroupEntity(groupId));
        imageRepository.save(imageEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(UUID imageId) {
        imageRepository.deleteById(imageId);
    }

    @Override
    @PreAuthorize("#userId.equals(authentication.principal) or hasAuthority('ADMIN')")
    public void unsigneeFromUser(UUID imageId, UUID userId) {
        ImageEntity imageEntity = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        imageEntity.getOwnersUsers().remove(userService.getUserEntity(userId));
        imageRepository.save(imageEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isAdmin(#groupId, authentication.principal)")
    public void unsigneeFromGroup(UUID imageId, UUID groupId) {
        ImageEntity imageEntity = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        imageEntity.getOwnersGroups().remove(groupService.getGroupEntity(groupId));
        imageRepository.save(imageEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or #userId.equals(authentication.principal)")
    public List<ImageResponse> getUserImages(UUID userId) {
        return imageRepository.findDistinctByOwnersUsers_id(userId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isUser(#groupId, authentication.principal)")
    public List<ImageResponse> getGroupImages(UUID groupId) {
        return imageRepository.findDistinctByOwnersGroups_id(groupId).stream().map(mapper::toResponse).toList();
    }


}