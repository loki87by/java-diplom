package ewm.Controllers;

import ewm.Objects.Compilation;
import ewm.Services.CompilationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("compilations")
@RequiredArgsConstructor
public class CompilationsController {
    private final CompilationService service;

    //PUBLIC
    @GetMapping("/")
    public List<Compilation> getCompilations(@RequestParam(defaultValue = "false") boolean pinned,
                                             @RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public Compilation getCurrent(@PathVariable Long compId) {
        //ToDo: check type of id and return exception
        return service.getCurrent(compId);
    }
}