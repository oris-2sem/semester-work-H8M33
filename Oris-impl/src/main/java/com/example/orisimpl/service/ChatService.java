package com.example.orisimpl.service;


import com.example.orisimpl.model.ChatEntity;
import dto.request.ChatRequest;
import dto.response.ChatResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ChatEntity createChat(UUID userId, ChatRequest request);

    ChatResponse updateChat(UUID chatId, ChatRequest request);

    void deleteChatById(UUID chatId);

    ChatResponse getChatById(UUID chatId);

    List<ChatResponse> getChats();

    List<ChatResponse> getChatsByUserId(UUID userID);

    ChatEntity getChatEntity(UUID chatId);

    void addUserToChat(UUID userId, UUID chatId);

    ChatResponse getChatByGroupId(UUID groupId);
}
