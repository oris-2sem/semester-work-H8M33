package com.example.orisimpl.repository;

import com.example.orisimpl.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    List<MessageEntity> findDistinctMessageByUser_Id(UUID id);

    List<MessageEntity> findDistinctMessageByChat_Id(UUID id);

    List<MessageEntity> findDistinctMessageByChat_IdAndUser_Id(UUID chatId, UUID userId);

    @Modifying
    @Query(value = "select (select * from message where message.user_id = a.id)  from refresh_token left join account a on a.id = refresh_token.account_id where refresh_token.id = :refreshTokenId;", nativeQuery = true)
    List<MessageEntity> findByRefreshTokenId(@Param("refreshTokenId") UUID refreshTokenId);
}
