package ewm.Controllers;

import ewm.Entityes.Category;
import ewm.Services.CategoryService;

import ewm.Errors.EntityNotFoundException;
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
    public Category getCategory(@PathVariable Long catId) {
        Category cat = service.getCategory(catId);
        //Todo: handle no long format id

        if(cat != null) {
            return cat;
        } else {
            throw new EntityNotFoundException("Category with id="+catId+" was not found");
        }
    }
}