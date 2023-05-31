package com.example.orisimpl.util.mapper;

import com.example.orisimpl.model.UserEntity;
import dto.request.UserRequest;
import dto.response.UserResponse;
import liquibase.util.MD5Util;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = ChatMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "hashPassword", source = "password")
    UserEntity toEntity(UserRequest request);

    UserResponse toResponse(UserEntity entity);
}
