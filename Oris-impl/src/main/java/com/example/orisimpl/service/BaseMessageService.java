package com.example.orisimpl.service;

import com.example.orisimpl.exception.MessageNotFoundException;
import com.example.orisimpl.model.ChatEntity;
import com.example.orisimpl.model.MessageEntity;
import com.example.orisimpl.repository.MessageRepository;
import com.example.orisimpl.util.mapper.MessageMapper;
import dto.request.MessageRequest;
import dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseMessageService implements MessageService{

    private final MessageRepository repository;
    private final MessageMapper mapper;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    @PreAuthorize("#userId.equals(authentication.principal) and @baseChatService.isUser(#chatId, #userId)")
    public void createMessage(UUID userId, UUID chatId, MessageRequest request) {
        MessageEntity entity = mapper.toEntity(request);
        entity.setUser(userService.getUserEntity(userId));
        ChatEntity chat = chatService.getChatEntity(chatId);
        entity.setChat(chat);
        chat.getMessages().add(entity);
        repository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') and @baseMessageService.isAuthor(#messageId,authentication.principal)")
    public MessageResponse updateMessage(UUID messageId, MessageRequest request) {
        Optional<MessageEntity> messageEntityOptional = repository.findById(messageId);
        if (messageEntityOptional.isPresent()){
            MessageEntity messageEntity = mapper.toEntity(request);
            messageEntity.setId(messageId);
            messageEntity.setChat(messageEntityOptional.get().getChat());
            messageEntity.setUser(messageEntityOptional.get().getUser());
            return mapper.toResponse(repository.save(messageEntity));
        }
        throw new MessageNotFoundException(messageId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') and @baseMessageService.isAuthor(#messageId,authentication.principal)")
    public void deleteMessageById(UUID messageId) {
        repository.deleteById(messageId);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public MessageResponse getMessageById(UUID messageId) {
       return repository.findById(messageId)
               .map(mapper::toResponse)
               .orElseThrow(()->new MessageNotFoundException(messageId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<MessageResponse> getMessages() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or #userId.equals(authentication.principal)")
    public List <MessageResponse> getMessagesByUserId(UUID userId) {
        return repository.findDistinctMessageByUser_Id(userId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @baseChatService.isUser(#chatId, authentication.principal)")
    public List <MessageResponse> getMessagesByChatId(UUID chatId) {
        return repository.findDistinctMessageByChat_Id(chatId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or (#userId.equals(authentication.principal) and " +
            "@baseChatService.isUser(#chatId, #userId))")
    public List<MessageResponse> getMessagesByChatIdAndUserId(UUID chatId, UUID userId) {
        return repository.findDistinctMessageByChat_IdAndUser_Id(chatId, userId).stream()
                .map(mapper::toResponse).toList();
    }

    public boolean isAuthor(UUID messageId, UUID userId){
        return repository.findById(messageId).orElseThrow(() -> new MessageNotFoundException(messageId))
                .getUser().getId().equals(userId);
    }

}
