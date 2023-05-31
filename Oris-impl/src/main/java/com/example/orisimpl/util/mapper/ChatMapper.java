package com.example.orisimpl.util.mapper;

import com.example.orisimpl.model.ChatEntity;
import dto.request.ChatRequest;
import dto.response.ChatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring", imports = UserMapper.class)
public interface ChatMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    ChatEntity toEntity(ChatRequest request);
    ChatResponse toResponse(ChatEntity entity);

    List<ChatResponse> toResponse(List<ChatEntity> entity);
}
