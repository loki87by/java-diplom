package ewm.Controllers;

import ewm.Entityes.Category;
import ewm.Entityes.StringObject;
import ewm.Services.CategoryService;
import ewm.Errors.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    private final CategoryService service;

    //ADMIN
    @PostMapping("/")
    //ToDo: Обратите внимание: имя категории должно быть уникальным
    public Category setCategory(@RequestHeader(value = "X-User-Id") Long userId,
                                @RequestBody StringObject dto) {
        return service.setCategory(userId, dto);
    }

    @PatchMapping("/{catId}")
    public StringObject updateCategory(@RequestHeader(value = "X-User-Id") Long userId,
                                       @RequestBody String name,
                                       @PathVariable Long catId) {
        return service.updateCategory(userId, catId, name);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategory(@RequestHeader(value = "X-User-Id") Long userId,
                                               @PathVariable Long catId) {

        if (service.deleteCategory(userId, catId)){
            return ResponseEntity.noContent().build();
        } else {
            throw new InternalServerErrorException("Failed to delete item");
        }
    }
}