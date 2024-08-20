package ewm.Repositoryes;

import ewm.Objects.Category;
import ewm.Utils.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepo {
    private final CategoryJPARepository jpa;
    public Category findById(Long id) {
            return jpa.findById(id).orElse(null);
    }

    public Category setCategory(Category category) {
        return jpa.saveAndFlush(category);
    }

    public boolean deleteItem(Long id) {
        Category current = findById(id);

        if(current == null) {
            throw new EntityNotFoundException("Category with id="+id+" was not found");
        }
        try {
            jpa.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Category> getCategories(int from, int size) {
        return jpa.findAllWithPagination(from, size);
    }

    public Category getCategory(Long id) {
        return jpa.findById(id).orElse(null);
    }
}
