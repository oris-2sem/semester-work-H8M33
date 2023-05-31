package api;

import dto.request.PostRequest;
import dto.response.PostResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Posts | Посты", value = "Пост")
@RequestMapping("/posts")
public interface PostApi {

    @ApiOperation(value = "Создание поста от имени пользователя", nickname = "post-create", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост создан"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    void createPostByUser(@PathVariable UUID userId,@RequestBody PostRequest request);

    @ApiOperation(value = "Создание поста от имени группы", nickname = "post-create", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост создан"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PostMapping("/group/{groupId}/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    void createPostByGroup(@PathVariable UUID groupId, @PathVariable UUID userId, @RequestBody PostRequest request);

    @ApiOperation(value = "Обновление поста", nickname = "post-update", response = PostResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост обновлён"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    PostResponse updatePost(@PathVariable UUID postId, @RequestBody PostRequest request);

    @ApiOperation(value = "Удаление поста", nickname = "post-delete", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост удалён"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    void deletePostById(@PathVariable UUID postId);

    @ApiOperation(value = "Получение поста", nickname = "post-get-by-id", response = PostResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    PostResponse getPostById(@PathVariable UUID postId);

    @ApiOperation(value = "Получение списка всех постов", nickname = "post-get-all", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список всех постов получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<PostResponse> getPosts();

    @ApiOperation(value = "Получение списка постов по айди автора-пользователя",
            nickname = "post-get-by-user-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<PostResponse> getPostsByUserId(@PathVariable UUID userId);

    @ApiOperation(value = "Получение списка постов по айди автора-группы",
            nickname = "post-get-by-group-id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")})
    @GetMapping("/group/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    List<PostResponse> getPostsByGroupId(@PathVariable UUID groupId);
}
