package ewm.Controllers;

import ewm.Dtos.EventCompDto;
import ewm.Dtos.FullEventDto;
import ewm.Entityes.Compilation;
import ewm.Entityes.CompilationRequest;
import ewm.Mappers.EventMapper;
import ewm.Services.CompilationService;
import ewm.Services.EventService;
import ewm.main_service.Utils;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationService service;
    private final EventService eService;
    private final EventMapper mapper;
    private final Utils utils;

    //ADMIN
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Compilation setCompilation(@RequestBody CompilationRequest comp) {
        boolean pinned = comp.getPinned();
        String title = comp.getTitle();
        List<Long> eventsIds = comp.getEvents();
        Compilation result = service.setCompilation(pinned, title);

        if (eventsIds != null && !eventsIds.isEmpty() && result.getId() != null) {
            List<EventCompDto> events = new ArrayList<>();

            for (Long id : eventsIds) {
                FullEventDto event = eService.getEvent(id);

                if (event != null) {
                    service.setCompilationEvent(id, result.getId());
                    events.add(mapper.toEventCompDto(event));
                }
            }
            result.setEvents(events);
        }
        return result;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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