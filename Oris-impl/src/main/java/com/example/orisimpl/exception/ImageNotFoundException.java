package com.example.orisimpl.exception;

import java.util.UUID;

public class ImageNotFoundException extends NotFoundException{

    public ImageNotFoundException(UUID imageId) {
        super(String.format("Image with this id = %s, not found", imageId));
    }
}
