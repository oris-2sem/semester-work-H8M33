package com.example.orisimpl.repository;

import com.example.orisimpl.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    List<PostEntity> getPostsByUser_Id(UUID userId);
}
