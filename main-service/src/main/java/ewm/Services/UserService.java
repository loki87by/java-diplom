package ewm.Services;

import ewm.Objects.User;
import ewm.Repositoryes.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;

    public List<User> getUsers(List<Long> ids,
                               int from,
                               int size) {
        return repo.getUsers(ids, from, size);
    }

    public User setUser(User user) throws BadRequestException {

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new BadRequestException("name");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new BadRequestException("email");
        }
        return repo.addUser(user);
    }

    public String deleteUser(Long id) throws ClassNotFoundException {
        User user = repo.findById(id);

        if(user != null) {
            repo.deleteUser(user);
            return "Пользователь удален";
        } else {
            throw new ClassNotFoundException();
        }
    }
}
