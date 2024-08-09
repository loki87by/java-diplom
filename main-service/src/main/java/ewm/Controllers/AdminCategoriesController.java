package ewm.Controllers;

import ewm.Objects.Category;
import ewm.Objects.StringObject;
import ewm.Services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryService service;

    //ADMIN
    @PostMapping("/")
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete item");
        }
    }
}