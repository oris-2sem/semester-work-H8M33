package com.example.orisimpl.security.model;

import com.example.orisimpl.model.AbstractEntity;
import com.example.orisimpl.security.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class RoleEntity extends AbstractEntity {

 @Enumerated(EnumType.STRING)
 private UserRole role;
}