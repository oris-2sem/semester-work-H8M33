package com.example.orisimpl.util.mapper;

import com.example.orisimpl.model.MessageEntity;
import dto.request.MessageRequest;
import dto.response.MessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {UserMapper.class, ChatMapper.class})
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    MessageEntity toEntity(MessageRequest request);

    MessageResponse toResponse(MessageEntity entity);
}
