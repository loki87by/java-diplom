package ewm.Services;

import ewm.Objects.User;
import ewm.Repositoryes.UserRepo;
import ewm.Utils.EntityNotFoundException;
import ewm.Utils.ValidationException;
import lombok.RequiredArgsConstructor;
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

    public User setUser(User user) {

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ValidationException("Field: name. Error: must not be blank. Value: null");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Field: email. Error: must not be blank. Value: null");
        }
        //ToDo: check email duplicates and return conflict
        return repo.addUser(user);
    }

    public String deleteUser(Long id) {
        User user = repo.findById(id);

        if(user != null) {
            repo.deleteUser(user);
            return "Пользователь удален";
        } else {
            throw new EntityNotFoundException("User with id="+id+" was not found");
        }
    }
}
