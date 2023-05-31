package com.example.orisimpl.controllers.rest;

import api.MessageApi;
import com.example.orisimpl.service.MessageService;
import dto.request.MessageRequest;
import dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService service;

    @Override
    public void createMessage(UUID userId, UUID chatId, MessageRequest request) {
        service.createMessage(userId, chatId,request);
    }

    @Override
    public MessageResponse updateMessage(UUID messageId, MessageRequest request) {
        return service.updateMessage(messageId, request);
    }

    @Override
    public void deleteMessageById(UUID messageId) {
        service.deleteMessageById(messageId);
    }

    @Override
    public MessageResponse getMessageById(UUID messageId) {
        return service.getMessageById(messageId);
    }

    @Override
    public List<MessageResponse> getMessages() {
        return service.getMessages();
    }

    @Override
    public List<MessageResponse> getMessagesByUserId(UUID userId) {
        return service.getMessagesByUserId(userId);
    }

    @Override
    public List<MessageResponse> getMessagesByChatId(UUID chatId) {
        return service.getMessagesByChatId(chatId);
    }

    @Override
    public List<MessageResponse> getMessagesByChatIdAndUserId(UUID chatId, UUID userId) {
        return service.getMessagesByChatIdAndUserId(chatId, userId);
    }

}
