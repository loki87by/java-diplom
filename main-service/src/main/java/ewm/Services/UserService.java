package ewm.Services;

import ewm.Entityes.User;
import ewm.Errors.ConflictException;
import ewm.Repositoryes.UserRepo;
import ewm.Errors.EntityNotFoundException;
import ewm.Errors.ValidationException;
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

        if (!repo.checkUniqueEmail(user.getEmail())) {
         throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_email];");
        }
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
