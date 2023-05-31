package api;

import dto.request.MessageRequest;
import dto.response.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Messages | Сообщения", value = "Сообщение")
@RequestMapping("/messages")
public interface MessageApi {

    @ApiOperation(value = "Создание сообщения", nickname = "message-create", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение создано"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PostMapping("/chat/{chatId}/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    void createMessage(@PathVariable UUID userId, @PathVariable UUID chatId, @RequestBody MessageRequest request);

    @ApiOperation(value = "Обновление сообщения", nickname = "message-update", response = MessageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение обновлено"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    MessageResponse updateMessage(@PathVariable UUID messageId, @RequestBody MessageRequest request);

    @ApiOperation(value = "Удаление сообщения", nickname = "message-delete", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение удалено"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @DeleteMapping("/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteMessageById(@PathVariable UUID messageId);

    @ApiOperation(value = "Получение сообщения по айди", nickname = "message-get-by-id", response = MessageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Сообщение получено"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    MessageResponse getMessageById(@PathVariable UUID messageId);

    @ApiOperation(value = "Получение списка всех сообщений", nickname = "message-get-list", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MessageResponse> getMessages();

    @ApiOperation(value = "Получение списка сообщений по айди автора-пользователя",
            nickname = "message-get-list-by-user-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<MessageResponse> getMessagesByUserId(@PathVariable UUID userId);

    @ApiOperation(value = "Получение списка всех сообщений по айди чата",
            nickname = "message-get-list-by-chat-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/chat/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    List<MessageResponse> getMessagesByChatId(@PathVariable UUID chatId);

    @ApiOperation(value = "Получение списка всех сообщений по айди чата и айди пользователя",
            nickname = "message-get-list-by-chat-id-and-user-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/chat/{chatId}/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<MessageResponse> getMessagesByChatIdAndUserId(@PathVariable UUID chatId, @PathVariable UUID userId);

}
