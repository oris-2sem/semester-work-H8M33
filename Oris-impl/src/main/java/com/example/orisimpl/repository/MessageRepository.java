package com.example.orisimpl.repository;

import com.example.orisimpl.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    List<MessageEntity> findDistinctMessageByUser_Id(UUID id);

    List<MessageEntity> findDistinctMessageByChat_Id(UUID id);

    List<MessageEntity> findDistinctMessageByChat_IdAndUser_Id(UUID chatId, UUID userId);
}
