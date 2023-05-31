package api;

import dto.request.GroupRequest;
import dto.response.GroupResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Groups | Группы", value = "Группа")
@RequestMapping("/groups")
public interface GroupApi {

    @ApiOperation(value = "Получение списка групп", nickname = "get-groups", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<GroupResponse> getGroups();

    @GetMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    GroupResponse getGroup(@PathVariable UUID groupId);

    @GetMapping("/user/{userId}")
    List<GroupResponse> getGroupsByUserId(@PathVariable UUID userId);

    @PostMapping("/{groupId}/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void addUserToGroup(@PathVariable UUID groupId, @PathVariable UUID userId);

    @DeleteMapping ("/{groupId}/remove/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void removeUserFromGroup(@PathVariable UUID groupId, @PathVariable UUID userId);

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    void createGroup(@PathVariable UUID userId, @RequestBody GroupRequest request);

    @PostMapping("/{groupId}/admin/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void addAdminToGroup(@PathVariable UUID groupId, @PathVariable UUID userId);

    @DeleteMapping ("/{groupId}/admin/remove/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void removeAdminFromGroup(@PathVariable UUID groupId, @PathVariable UUID userId);

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteGroup(@PathVariable UUID groupId);

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    void updateGroup(@PathVariable UUID groupId, @RequestBody GroupRequest request );
}
