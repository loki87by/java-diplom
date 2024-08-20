package ewm.Services;

import ewm.Objects.Category;
import ewm.Objects.StringObject;
import ewm.Objects.User;
import ewm.Repositoryes.CategoryRepo;
import ewm.Repositoryes.UserRepo;
import ewm.Utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    private boolean checkAdmin(Long userId) {
        User user = userRepo.findById(userId);
        return user.getIsAdmin();
    }

    public Category setCategory(Long userId, StringObject data) {
        if (!checkAdmin(userId)) {
            throw new ValidationException("not admin");
        } else {
            Category category = new Category();
            category.setName(data.getName());
            return categoryRepo.setCategory(category);
        }
    }

    public StringObject updateCategory(Long userId, Long catId, String name) {

        if (!checkAdmin(userId)) {
            throw new ValidationException("not admin");
        } else {
            Category category = categoryRepo.findById(catId);
            category.setName(name);
            Category updatedCategory = categoryRepo.setCategory(category);
            StringObject dto = new StringObject();
            dto.setName(updatedCategory.getName());
            return dto;
        }
    }

    public boolean deleteCategory(Long userId, Long catId) {
        //toDo: check no empty category and return conflict

        if (!checkAdmin(userId)) {
            throw new ValidationException("not admin");
        } else {
            return categoryRepo.deleteItem(catId);
        }
    }

    public List<Category> getCategories (int from,
                                         int size) {
        return categoryRepo.getCategories(from, size);
    }

    public Category getCategory(Long id) {
        return categoryRepo.getCategory(id);
    }
}
