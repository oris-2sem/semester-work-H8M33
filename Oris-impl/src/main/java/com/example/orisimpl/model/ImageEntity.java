package com.example.orisimpl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "image_data")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity extends AbstractEntity {

    private String type;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @ManyToOne
    @JoinTable(
            name = "image_owner",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    UserEntity author;

    @ManyToMany
    @JoinTable(
            name = "image_account",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    Set<UserEntity> ownersUsers;

    @ManyToMany
    @JoinTable(
            name = "image_group",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    Set<GroupEntity> ownersGroups;
}