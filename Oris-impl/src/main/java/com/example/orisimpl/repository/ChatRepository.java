package com.example.orisimpl.repository;

import com.example.orisimpl.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

    Optional<ChatEntity> findDistinctChatByUsers_Id(UUID chatId);

    Optional<ChatEntity> findChatEntityByGroup_Id(UUID groupId);
}
