package com.example.orisimpl.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Table(name = "post")
@Entity
@SuperBuilder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class PostEntity extends AbstractEntity{

    private String title;

    private String post_text;

    @ManyToOne
    @JoinTable(
            name = "post_account",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private UserEntity user;

    @ManyToOne
    @JoinTable(
            name = "post_group",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private GroupEntity group;

}
