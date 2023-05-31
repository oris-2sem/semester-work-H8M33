package com.example.orisimpl.repository;

import com.example.orisimpl.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

    List<GroupEntity> findDistinctByUsers_id(UUID userId);
}
