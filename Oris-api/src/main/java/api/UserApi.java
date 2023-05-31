package api;

import dto.request.UserRequest;
import dto.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Users | Пользователи", value = "Пользователь")
@RequestMapping("/users")
public interface UserApi {

    @PostMapping("/activate/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void activateUser(@PathVariable UUID userId);

    @PostMapping("/ban/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void banUser(@PathVariable UUID userId);

    @ApiOperation(value = "Создание пользователя", nickname = "user-create", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@RequestBody UserRequest request);

    @ApiOperation(value = "Обновление пользователя", nickname = "user-update", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлён"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse updateUser(@PathVariable UUID userId, @RequestBody UserRequest request);

    @ApiOperation(value = "Удаление пользователя", nickname = "user-delete", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удалён"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUserById(@PathVariable UUID userId);

    @ApiOperation(value = "Получение пользователя по айди", nickname = "user-by-id", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse getUserById(@PathVariable UUID userId);

    @ApiOperation(value = "Получение списка всех пользователей", nickname = "user-get-all", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserResponse> getUsers();

    @ApiOperation(value = "Получение списка всех участников чата", nickname = "user-get-by-chat-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/chat/{chatID}")
    @ResponseStatus (HttpStatus.OK)
    List<UserResponse> getUsersByChatId(@PathVariable UUID chatID);

    @ApiOperation(value = "Получение автора поста по айди поста", nickname = "user-get-by-post-id", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/post/{postId}")
    @ResponseStatus (HttpStatus.OK)
    UserResponse getUserByPostId(@PathVariable UUID postId);

    @ApiOperation(value = "Получение списка пользователей по айди группы", nickname = "users-get-by-group-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователи получены"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/group/{groupId}")
    @ResponseStatus (HttpStatus.OK)
    List<UserResponse> getUsersByGroupId(@PathVariable UUID groupId);

    @ApiOperation(value = "Получение списка админов по айди группы", nickname = "users-get-by-group-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Админы получены"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/group/admin/{groupId}")
    @ResponseStatus (HttpStatus.OK)
    List<UserResponse> getAdminsByGroupId(@PathVariable UUID groupId);

    @ApiOperation(value = "Назначить пользователю роль администратора", nickname = "set-admin-role", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Роль назначена"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/admin/setAdmin/{userId}")
    @ResponseStatus (HttpStatus.OK)
    void setAdminRole(@PathVariable UUID userId);

    @ApiOperation(value = "Назначить пользователю базовую роль", nickname = "set-user-role", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Роль назначена"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/admin/setUser/{userId}")
    @ResponseStatus (HttpStatus.OK)
    void setUserRole(@PathVariable UUID userId);
}
