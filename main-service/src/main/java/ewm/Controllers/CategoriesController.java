package ewm.Controllers;

import ewm.Entityes.Category;
import ewm.Services.CategoryService;

import ewm.Errors.EntityNotFoundException;
import ewm.main_service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService service;
    private final Utils utils;

    //PUBLIC
    @GetMapping("")
    public List<Category> getCategories(@RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public Category getCategory(@PathVariable Object catId) {
        Long id = utils.idValidation(catId);
        Category cat = service.getCategory(id);

        if (cat != null) {
            return cat;
        } else {
            throw new EntityNotFoundException("Category with id=" + id + " was not found");
        }
    }
}