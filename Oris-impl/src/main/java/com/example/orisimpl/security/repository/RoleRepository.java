package com.example.orisimpl.security.repository;

import com.example.orisimpl.security.enums.UserRole;
import com.example.orisimpl.security.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByRole(UserRole role);
}
