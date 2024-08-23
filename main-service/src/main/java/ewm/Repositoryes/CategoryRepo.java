package ewm.Repositoryes;

import ewm.Entityes.Category;
import ewm.Errors.ConflictException;
import ewm.Errors.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        if (!checkUniqueName(category.getName())) {
            throw new ConflictException("duplicate name");
        }
        return jpa.saveAndFlush(category);
    }

    public boolean checkUniqueName(String name) {
        Category current = jpa.findCategoryByName(name).orElse(null);
        return current == null;
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
        Pageable page = PageRequest.of(from, size);
        return jpa.findAllWithPagination(page);
    }

    public Category getCategory(Long id) {
        return jpa.findById(id).orElse(null);
    }
}
