package ewm.Repositoryes;

import ewm.Objects.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepo {
    private final UserJPARepository jpa;
    public User findById(Long id) {
            return jpa.findById(id).orElse(null);
    }

    public List<User> getUsers(List<Long> ids,
                               int from,
                               int size) {
        Pageable pageable = PageRequest.of(from, size);

        if (ids != null) {
            return jpa.findByIdIn(ids, pageable).getContent();
        } else {
            return jpa.findAll(pageable).getContent();
        }
    }

    public User addUser(User user) {
        return jpa.saveAndFlush(user);
    }

    public void deleteUser(User user) {
        jpa.delete(user);
    }
}
