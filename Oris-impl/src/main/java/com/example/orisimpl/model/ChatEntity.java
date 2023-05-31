package com.example.orisimpl.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "chat")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity extends AbstractEntity{

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
            name ="account_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns =@JoinColumn(name = "account_id")
    )
    private Set<UserEntity> users;

    @ManyToMany
    @JoinTable(
            name ="chat_admin",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns =@JoinColumn(name = "account_id")
    )
    private Set<UserEntity> admins;

    @OneToMany(mappedBy = "chat")
    private Set<MessageEntity> messages;

    @OneToOne
    @JoinTable(
            name = "users_group",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn (name = "id")
    )
    private GroupEntity group;
}
