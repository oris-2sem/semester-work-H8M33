package com.example.orisimpl.controllers.rest;

import api.ChatApi;
import com.example.orisimpl.service.ChatService;
import dto.request.ChatRequest;
import dto.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatApi {

    private final ChatService service;

    @Override
    public void createChat(UUID userId, ChatRequest request) {
        service.createChat(userId, request);
    }

    @Override
    public void addUser(UUID userId, UUID chatId) {
        service.addUserToChat(userId, chatId);
    }

    @Override
    public ChatResponse updateChat(UUID chatId, ChatRequest request) {
        return service.updateChat(chatId,request);
    }

    @Override
    public void deleteChatById(UUID chatId) {
        service.deleteChatById(chatId);
    }

    @Override
    public ChatResponse getChatById(UUID chatId) {
        return service.getChatById(chatId);
    }

    @Override
    public List<ChatResponse> getChatsByUserId(UUID userId) {
        return service.getChatsByUserId(userId);
    }

    @Override
    public List<ChatResponse> getChats() {
        return service.getChats();
    }

    @Override
    public ChatResponse getChatByGroupId(UUID groupId) {
        return service.getChatByGroupId(groupId);
    }
}
