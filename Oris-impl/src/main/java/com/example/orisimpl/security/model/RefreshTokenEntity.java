package com.example.orisimpl.security.model;

import com.example.orisimpl.model.AbstractEntity;
import com.example.orisimpl.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "refresh_token")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "account_id")
    UserEntity user;

    @Column(name = "refresh_token")
    String refreshToken;
}
