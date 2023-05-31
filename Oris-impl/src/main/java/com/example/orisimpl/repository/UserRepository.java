package com.example.orisimpl.repository;

import com.example.orisimpl.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> getUsersByChats_Id(UUID chatID);

    UserEntity getUserByPosts_Id(UUID postId);

    List<UserEntity> getUsersByGroups_Id(UUID groupID);

    List<UserEntity> getUsersByGroupAdmin_Id(UUID groupID);


    Optional<UserEntity> getUserByUsername(String username);
}
