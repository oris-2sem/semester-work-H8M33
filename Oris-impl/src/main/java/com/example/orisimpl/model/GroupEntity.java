package com.example.orisimpl.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "users_group")
@Entity
@SuperBuilder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupEntity extends AbstractEntity{

    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @ManyToMany
    @JoinTable(
            name ="account_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns =@JoinColumn(name = "account_id")
    )
    private Set<UserEntity> users;

    @ManyToMany
    @JoinTable(
            name ="group_admin",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns =@JoinColumn(name = "account_id")
    )
    private Set<UserEntity> admins;

    @OneToMany
    @JoinTable(
            name = "post_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<PostEntity> posts;

    @ManyToMany
    @JoinTable(
            name = "image_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    Set<ImageEntity> saved_images;
}
