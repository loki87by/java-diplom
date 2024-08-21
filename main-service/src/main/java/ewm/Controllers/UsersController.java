package ewm.Controllers;

import ewm.Entityes.User;
import ewm.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;

    //ADMIN
    @GetMapping("")
    public List<User> getUsers(
            @RequestParam (required = false) List<Long> ids,
            @RequestParam (defaultValue = "0") int from,
            @RequestParam (defaultValue = "10") int size) {
        return service.getUsers(ids, from, size);
    }
    @PostMapping("")
    public User setUser(
            @RequestBody User user) {
        return service.setUser(user);
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(
            @PathVariable Long userId){
        return service.deleteUser(userId);
    }

}