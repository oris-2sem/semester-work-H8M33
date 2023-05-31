package com.example.orisimpl.model;

import javax.persistence.*;

import com.example.orisimpl.model.enums.Status;
import com.example.orisimpl.security.enums.UserRole;
import com.example.orisimpl.security.model.RoleEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
            name ="account_chat",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns =@JoinColumn(name = "chat_id")
    )
    private Set<ChatEntity> chats;

    @ManyToMany
    @JoinTable(
            name ="account_group",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns =@JoinColumn(name = "group_id")
    )
    private Set<GroupEntity> groups;

    @ManyToMany
    @JoinTable(
            name ="group_admin",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns =@JoinColumn(name = "group_id")
    )
    private Set<GroupEntity> groupAdmin;

    @OneToMany
    @JoinTable(
            name = "post_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<PostEntity> posts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",
                    referencedColumnName = "id")}
    )
    private Set<RoleEntity> roles;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    @JoinTable(
            name = "image_owner",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    Set<ImageEntity> author_images;

    @ManyToMany
    @JoinTable(
            name = "image_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    Set<ImageEntity> saved_images;
}
