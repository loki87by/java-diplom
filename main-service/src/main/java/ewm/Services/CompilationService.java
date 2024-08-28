package ewm.Services;

import ewm.Dtos.EventCompDto;
import ewm.Entityes.CompilationEvents;
import ewm.Errors.ConflictException;
import ewm.Mappers.EventMapper;
import ewm.Entityes.Compilation;
import ewm.Entityes.CompilationRequest;
import ewm.Entityes.Event;
import ewm.Repositoryes.CompilationsRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Errors.EntityNotFoundException;
import ewm.Errors.ForbiddenException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationsRepo repo;
    private final EventsRepo eRepo;
    private final EventMapper mapper;

    private List<EventCompDto> getEvents(Long compilationId) {
        List<EventCompDto> events = new ArrayList<>();
        List<Event> eventsFromDb = eRepo.getEvents(compilationId);

        if (eventsFromDb != null) {
            for (Event event : eventsFromDb) {
                events.add(mapper.toEventCompDto(event));
            }
        }
        return events;
    }

    public List<Compilation> getCompilations(boolean pinned,
                                             int from,
                                             int size) {
        List<Compilation> comps = new ArrayList<>();

        for (Compilation compilation : repo.getCompilations(pinned, from, size)) {
            List<EventCompDto> events = getEvents(compilation.getId());
            compilation.setEvents(events);
            comps.add(compilation);
        }
        return comps;
    }

    @Transactional
    public Compilation setCompilation(boolean pinned,
                                      String title) {
        return repo.setEmptyCompilation(pinned, title);
    }

    public void setCompilationEvent(Long eId, Long cId) {
        CompilationEvents cEvent = new CompilationEvents(eId, cId);
        repo.setCompilationEvent(cEvent);
    }

    public Compilation getCurrent(Long id) {
        Compilation comp = repo.getCurrent(id);

        if (comp == null) {
            throw new EntityNotFoundException("Compilation with id=" + id + " was not found");
        }
        List<EventCompDto> events = getEvents(comp.getId());
        comp.setEvents(events);
        return comp;
    }

    public String deleteCompilation(Compilation comp) {
        return repo.deleteCompilation(comp);
    }

    public Compilation changeCompilation(CompilationRequest comp, Long id) {

        if (!repo.checkUniqueTitle(comp.getTitle(), id)) {
            throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_compilation_name];");
        }
        Compilation old = repo.getCurrent(id);
        old.setPinned(comp.getPinned());
        String title = comp.getTitle();

        if (!title.isEmpty()) {
            old.setTitle(title);
        } else {
            throw new ForbiddenException("field title was not been empty");
        }
        List<Long> eventIds = comp.getEvents();
        List<CompilationEvents> cEvents = repo.getCompilationEvents(id);

        for (CompilationEvents cEvent : cEvents) {
            Long eventId = cEvent.getEventId();

            if (!eventIds.contains(eventId)) {
                Long ceId = cEvent.getId();
                repo.removeCompilationEvents(ceId);
            }
        }
        List<EventCompDto> currentEvents = new ArrayList<>();

        for (Long currentId : eventIds) {
            List<CompilationEvents> currEvents = cEvents
                    .stream()
                    .filter(event -> {
                        Long evId = event.getEventId();
                        return evId != null && evId.equals(currentId);
                    })
                    .toList();

            if (currEvents.isEmpty()) {
                Event currEvent = eRepo.getEvent(currentId);

                if (currEvent != null) {
                    Long curId = currEvent.getId();
                    CompilationEvents evnt = new CompilationEvents(curId, id);
                    repo.setCompilationEvent(evnt);
                }
            }
            currentEvents.add(mapper.toEventCompDto(eRepo.getEvent(currentId)));
        }
        old.setEvents(currentEvents);
        return repo.getCurrent(id);
    }
}
