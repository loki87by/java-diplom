package ewm.Controllers;

import ewm.Dtos.CategoryDto;
import ewm.Entityes.Category;
import ewm.Entityes.StringObject;
import ewm.Services.CategoryService;
import ewm.Errors.InternalServerErrorException;
import ewm.main_service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    private final CategoryService service;
    private final Utils utils;

    //ADMIN
    @PostMapping("")
    public Category setCategory(@RequestHeader(value = "X-User-Id") Object userId,
                                @RequestBody StringObject dto) {
        Long id = utils.idValidation(userId);
        return service.setCategory(id, dto);
    }

    @PatchMapping("/{catId}")
    public StringObject updateCategory(@RequestHeader(value = "X-User-Id") Object userId,
                                       @RequestBody CategoryDto dto,
                                       @PathVariable Object catId) {
        Long uId = utils.idValidation(userId);
        Long cId = utils.idValidation(catId);
        return service.updateCategory(uId, cId, dto.getName());
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<String> deleteCategory(@RequestHeader(value = "X-User-Id") Object userId,
                                               @PathVariable Object catId) {
        Long uId = utils.idValidation(userId);
        Long cId = utils.idValidation(catId);

        if (service.deleteCategory(uId, cId)){
            return ResponseEntity.noContent().build();
        } else {
            throw new InternalServerErrorException("Failed to delete item");
        }
    }
}