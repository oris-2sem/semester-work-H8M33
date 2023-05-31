package com.example.orisimpl.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Table(name = "message")
@Entity
@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MessageEntity extends AbstractEntity {

    @Column(nullable = false)
    private String message_text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;
}
