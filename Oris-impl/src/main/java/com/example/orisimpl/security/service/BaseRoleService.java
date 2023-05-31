package com.example.orisimpl.security.service;

import com.example.orisimpl.security.enums.UserRole;
import com.example.orisimpl.security.model.RoleEntity;
import com.example.orisimpl.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BaseRoleService implements RoleService{

    private final RoleRepository repository;

    @Override
    public Optional<RoleEntity> findRole(UserRole role) {
        return repository.findByRole(role);
    }
}
