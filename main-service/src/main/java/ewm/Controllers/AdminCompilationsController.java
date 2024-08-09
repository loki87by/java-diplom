package ewm.Controllers;

import ewm.Objects.Compilation;
import ewm.Objects.CompilationRequest;
import ewm.Services.CompilationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationService service;

    //ADMIN
    @PostMapping("/")
    public Compilation setCompilation(@RequestBody CompilationRequest comp) {
        boolean pinned = comp.getPinned();
        String title = comp.getTitle();
        List<Long> events = comp.getEvents();
        return service.setCompilation(pinned, title, events);
    }

    @DeleteMapping("/{compId}")
    public String deleteCompilation(@PathVariable Long compId) throws ClassNotFoundException {
        Compilation comp = service.getCurrent(compId);
        return service.deleteCompilation(comp);
    }

    @PatchMapping("/{compId}")
    public Compilation changeCompilation(@RequestBody CompilationRequest comp, @PathVariable Long compId) {
        return service.changeCompilation(comp, compId);
    }
}