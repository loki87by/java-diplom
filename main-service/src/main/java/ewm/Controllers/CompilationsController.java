package ewm.Controllers;

import ewm.Entityes.Compilation;
import ewm.Services.CompilationService;
import ewm.main_service.Utils;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationsController {
    private final CompilationService service;
    private final Utils utils;

    //PUBLIC
    @GetMapping("")
    public List<Compilation> getCompilations(@RequestParam(defaultValue = "false") boolean pinned,
                                             @RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public Compilation getCurrent(@PathVariable Object compId) {
        Long id = utils.idValidation(compId);
        return service.getCurrent(id);
    }
}