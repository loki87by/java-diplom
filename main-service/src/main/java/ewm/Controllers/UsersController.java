package ewm.Controllers;

import ewm.Entityes.User;
import ewm.Services.UserService;
import ewm.main_service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;
    private final Utils utils;

    //ADMIN
    @GetMapping("")
    public List<User> getUsers(
            @RequestParam(required = false) List<Object> ids,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        List<Long> uIds = new ArrayList<>();
        for (Object id : ids) {
            Long uId = utils.idValidation(id);
            uIds.add(uId);
        }
        return service.getUsers(uIds, from, size);
    }

    @PostMapping("")
    public User setUser(
            @RequestBody User user) {
        return service.setUser(user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(
            @PathVariable Object userId) {
        Long uId = utils.idValidation(userId);
        return service.deleteUser(uId);
    }
}