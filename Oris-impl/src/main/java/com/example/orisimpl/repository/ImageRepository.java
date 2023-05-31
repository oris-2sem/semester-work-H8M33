package com.example.orisimpl.repository;

import com.example.orisimpl.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findById(UUID imageId);

    void deleteById(UUID imageId);

    List<ImageEntity> findDistinctByOwnersUsers_id(UUID userId);

    List<ImageEntity> findDistinctByOwnersGroups_id(UUID groupId);
}