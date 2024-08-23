package ewm.Controllers;

import ewm.Dtos.EventDto;
import ewm.Entityes.Compilation;
import ewm.Entityes.CompilationRequest;
import ewm.Entityes.Event;
import ewm.Mappers.EventMapper;
import ewm.Services.CompilationService;
import ewm.main_service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationService service;
    private final EventMapper mapper;
    private final Utils utils;

    //ADMIN
    @PostMapping("")
    public Compilation setCompilation(@RequestBody CompilationRequest comp) {
        boolean pinned = comp.getPinned();
        String title = comp.getTitle();
        List<Long> eventsIds = comp.getEvents();
        Compilation result = service.setCompilation(pinned, title);
        if (eventsIds != null && !eventsIds.isEmpty() && result.getId() != null) {
            List<EventDto> events = new ArrayList<>();

            for (Long id : eventsIds) {
                Event event = service.insertToEvent(id, result.getId());
                events.add(mapper.toObject(event));
            }
            result.setEvents(events);
        }
        return result;
    }

    @DeleteMapping("/{compId}")
    public String deleteCompilation(@PathVariable Object compId) {
        Long id = utils.idValidation(compId);
        Compilation comp = service.getCurrent(id);
        return service.deleteCompilation(comp);
    }

    @PatchMapping("/{compId}")
    public Compilation changeCompilation(@RequestBody CompilationRequest comp, @PathVariable Object compId) {
        Long id = utils.idValidation(compId);
        return service.changeCompilation(comp, id);
    }
}