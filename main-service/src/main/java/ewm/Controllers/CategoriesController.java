package ewm.Controllers;

import ewm.Objects.Category;
import ewm.Services.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService service;

    //PUBLIC
    @GetMapping("/")
    public List<Category> getCategories(@RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public Category getCategory(@PathVariable Long catId) throws ClassNotFoundException {
        Category cat = service.getCategory(catId);

        if(cat != null) {
            return cat;
        } else {
            throw new ClassNotFoundException();
        }
    }
}