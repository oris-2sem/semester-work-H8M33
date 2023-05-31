package com.example.orisimpl.util.mapper;

import com.example.orisimpl.model.GroupEntity;
import dto.request.GroupRequest;
import dto.response.GroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {UserMapper.class, ChatMapper.class})
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    GroupEntity toEntity(GroupRequest request);

    GroupResponse toResponse(GroupEntity entity);
}
