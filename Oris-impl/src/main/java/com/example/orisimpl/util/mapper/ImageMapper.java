package com.example.orisimpl.util.mapper;

import com.example.orisimpl.model.ImageEntity;
import dto.response.ImageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageResponse toResponse(ImageEntity entity);
}
