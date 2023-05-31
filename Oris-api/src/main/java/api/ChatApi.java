package api;

import dto.request.ChatRequest;
import dto.response.ChatResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Api (tags = "Chats | Чаты", value = "Чат")
@RequestMapping("/chats")
public interface ChatApi {

    @ApiOperation(value = "Создание чата", nickname = "chat-create", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат создан"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PostMapping({"/{userId}"})
    @ResponseStatus(HttpStatus.CREATED)
    void createChat(@PathVariable UUID userId, @RequestBody ChatRequest request);

    @ApiOperation(value = "Добавление пользователя в чат", nickname = "add-user-to-chat", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь добавлен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/{chatId}/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void addUser(@PathVariable UUID userId, @PathVariable UUID chatId);

    @ApiOperation(value = "Обновление чата", nickname = "chat-update", response = ChatResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат обновлён", response = ChatResponse.class),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/{chatId}/update")
    @ResponseStatus(HttpStatus.OK)
    ChatResponse updateChat(@PathVariable UUID chatId, @RequestBody ChatRequest request);

    @ApiOperation(value = "Удаление чата", nickname = "chat-delete", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат удалён"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @DeleteMapping("/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteChatById(@PathVariable UUID chatId);

    @ApiOperation(value = "Найти чат по айди", nickname = "chat-get-by-id", response = ChatResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат найден", response = ChatResponse.class),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    ChatResponse getChatById(@PathVariable UUID chatId);

    @ApiOperation(value = "Получение всех чатов пользователя", nickname = "chat-list-get-by-user-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список составлен", response = List.class),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<ChatResponse> getChatsByUserId(@PathVariable UUID userId);

    @ApiOperation(value = "Получить список всех чатов", nickname = "chat-list-get", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список составлен", response = List.class),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ChatResponse> getChats();

    @ApiOperation(value = "Получение чата группы", nickname = "chat-get-by-group-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат получен", response = List.class),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/group/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    ChatResponse getChatByGroupId (@PathVariable UUID groupId);

}
