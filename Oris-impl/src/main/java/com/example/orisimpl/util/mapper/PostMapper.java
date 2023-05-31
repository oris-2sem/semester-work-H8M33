package com.example.orisimpl.util.mapper;

import com.example.orisimpl.model.PostEntity;
import dto.request.PostRequest;
import dto.response.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = UserMapper.class)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    PostEntity toEntity(PostRequest request);
    PostResponse toResponse(PostEntity entity);

    List<PostResponse> toResponse(List<PostEntity> entity);
}
