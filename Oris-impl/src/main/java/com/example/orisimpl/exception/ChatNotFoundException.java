package com.example.orisimpl.exception;

import java.util.UUID;

public class ChatNotFoundException extends NotFoundException{
    public ChatNotFoundException(UUID chatId) {
        super(String.format("Chat with this id = %s, not found", chatId));
    }
}
