package com.example.orisimpl.service;

import com.example.orisimpl.exception.ChatNotFoundException;
import com.example.orisimpl.exception.GroupNotFoundException;
import com.example.orisimpl.model.AbstractEntity;
import com.example.orisimpl.model.ChatEntity;
import com.example.orisimpl.model.UserEntity;
import com.example.orisimpl.repository.ChatRepository;
import com.example.orisimpl.util.mapper.ChatMapper;
import dto.request.ChatRequest;
import dto.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseChatService implements ChatService{

    private final ChatRepository repository;
    private final ChatMapper mapper;
    private final UserService userService;


    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or #userId.equals(authentication.principal) ")
    public ChatEntity createChat(UUID userId, ChatRequest request) {
        ChatEntity entity = mapper.toEntity(request);
        HashSet<UserEntity> listUsers = new HashSet<>();
        listUsers.add(userService.getUserEntity(userId));
        entity.setMessages(new HashSet<>());
        entity.setUsers(listUsers);
        entity.setAdmins(listUsers);
        return repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or @baseChatService.isAdmin(#chatId, authentication.principal)")
    public ChatResponse updateChat(UUID chatId, ChatRequest request) {
        Optional<ChatEntity> chatEntityOptional = repository.findById(chatId);
        if (chatEntityOptional.isPresent()){
            ChatEntity chatEntity = mapper.toEntity(request);
            chatEntity.setId(chatId);
            chatEntity.setUsers(chatEntityOptional.get().getUsers());
            chatEntity.setMessages(chatEntityOptional.get().getMessages());
            return mapper.toResponse(repository.save(chatEntity));
        }
        throw new ChatNotFoundException(chatId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or @baseChatService.isAdmin(#chatId, authentication.principal)")
    public void deleteChatById(UUID chatId) {
        repository.deleteById(chatId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or @baseChatService.isUser(#chatId, authentication.principal)")
    public ChatResponse getChatById(UUID chatId) {
        return repository.findById(chatId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ChatResponse> getChats() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or #userID.equals(authentication.principal) ")
    public List<ChatResponse> getChatsByUserId(UUID userID) {
        return repository.findDistinctChatByUsers_Id(userID).stream().map(mapper::toResponse).toList();
    }

    @Override
    public ChatEntity getChatEntity(UUID chatId) {
        Optional<ChatEntity> chatEntityOptional = repository.findById(chatId);
        if (chatEntityOptional.isPresent()){
            return chatEntityOptional.get();
        }
        throw new ChatNotFoundException(chatId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')"
            + "or @baseChatService.isAdmin(#chatId, authentication.principal)")
    public void addUserToChat(UUID userId, UUID chatId) {
        ChatEntity entity = getChatEntity(chatId);
        entity.getUsers().add(userService.getUserEntity(userId));
        repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseGroupService.isUser(#groupId, authentication.principal)")
    public ChatResponse getChatByGroupId(UUID groupId) {
        return repository.findChatEntityByGroup_Id(groupId).map(mapper::toResponse).orElseThrow(() -> new GroupNotFoundException(groupId));
    }
    public boolean isAdmin(UUID chatId, UUID userId){
        return repository.findById(chatId).orElseThrow(() -> new GroupNotFoundException(chatId))
                .getAdmins().stream().map(AbstractEntity::getId).toList().contains(userId);
    }

    public boolean isUser(UUID chatId, UUID userId){
        return repository.findById(chatId).orElseThrow(() -> new GroupNotFoundException(chatId))
                .getUsers().stream().map(AbstractEntity::getId).toList().contains(userId);
    }

}
