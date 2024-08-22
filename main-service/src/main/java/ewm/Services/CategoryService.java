package ewm.Services;

import ewm.Entityes.Category;
import ewm.Entityes.Event;
import ewm.Entityes.StringObject;
import ewm.Entityes.User;
import ewm.Errors.ConflictException;
import ewm.Repositoryes.CategoryRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Repositoryes.UserRepo;
import ewm.Errors.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final EventsRepo eventRepo;

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

            if (!categoryRepo.checkUniqueName(name)) {
                throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_category_name];");
            }
            category.setName(name);
            Category updatedCategory = categoryRepo.setCategory(category);
            StringObject dto = new StringObject();
            dto.setName(updatedCategory.getName());
            return dto;
        }
    }

    public boolean deleteCategory(Long userId, Long catId) {
        List<Event> events = eventRepo.findAllByCategoryId(catId);

        if (!events.isEmpty()) {
            throw new ConflictException("The category is not empty");
        }

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
