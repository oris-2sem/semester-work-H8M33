package com.example.orisimpl.security.service;

import com.example.orisimpl.security.enums.UserRole;
import com.example.orisimpl.security.model.RoleEntity;

import java.util.Optional;

public interface RoleService {

    Optional<RoleEntity> findRole(UserRole role);
}
