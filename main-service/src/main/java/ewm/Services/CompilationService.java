package ewm.Services;

import ewm.Dtos.EventDto;
import ewm.Mappers.EventMapper;
import ewm.Objects.Compilation;
import ewm.Objects.CompilationRequest;
import ewm.Objects.Event;
import ewm.Repositoryes.CompilationsRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Utils.EntityNotFoundException;
import ewm.Utils.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationsRepo repo;
    private final EventsRepo eRepo;
    private final EventMapper mapper;

    private List<EventDto> getEvents(Long compilationId) {
        List<EventDto> events = new ArrayList<>();
        List<Event> eventsFromDb = eRepo.getEvents(compilationId);

        if (eventsFromDb != null) {
            for (Event event : eventsFromDb) {
                events.add(mapper.toObject(event));
            }
        }
        return events;
    }

    public List<Compilation> getCompilations(boolean pinned,
                                             int from,
                                             int size) {
        List<Compilation> comps = new ArrayList<>();

        for (Compilation compilation : repo.getCompilations(pinned, from, size)) {
            List<EventDto> events = getEvents(compilation.getId());
            compilation.setEvents(events);
            comps.add(compilation);
        }
        return comps;
    }

    public Compilation setCompilation(boolean pinned,
                                      String title,
                                      List<Long> eventsIds) {
        Compilation comp = repo.setEmptyCompilation(pinned, title);

        if (eventsIds == null || eventsIds.isEmpty()) {
            return comp;
        } else {
            List<EventDto> events = new ArrayList<>();

            for (Long id : eventsIds) {
                Event event = eRepo.getEvent(id);
                event.setCategoryId(comp.getId());
                events.add(mapper.toObject(event));
            }
            comp.setEvents(events);
            return comp;
        }
    }

    public Compilation getCurrent(Long id) {
        Compilation comp = repo.getCurrent(id);

        if (comp == null) {
            throw new EntityNotFoundException("Compilation with id="+id+" was not found");
        }
        List<EventDto> events = getEvents(comp.getId());
        comp.setEvents(events);
        return comp;
    }

    public String deleteCompilation(Compilation comp) {
        return repo.deleteCompilation(comp);
    }

    public Compilation changeCompilation(CompilationRequest comp, Long id) {
        Compilation old = repo.getCurrent(id);
        old.setPinned(comp.getPinned());
        String title = comp.getTitle();

        if (!title.isEmpty()) {
            old.setTitle(title);
        } else {
            throw new ForbiddenException("field title was not been empty");
        }
        List<Long> eventIds = comp.getEvents();
        List<Event> events = eRepo.findAllByCategoryId(id);

        for (Event event : events) {
            Long eventId = event.getId();

            if (!eventIds.contains(eventId)) {
                event.setCategoryId(null);
                eRepo.setEvent(event);
            }
        }

        for (Long currentId : eventIds) {
            Event currEvent = events
                    .stream()
                    .filter(event -> event.getCompilationId().equals(currentId))
                    .toList()
                    .getFirst();

            if(currEvent == null) {
                currEvent = eRepo.getEvent(currentId);
                currEvent.setCompilationId(id);
                eRepo.setEvent(currEvent);
            }
        }
        return repo.getCurrent(id);
    }
}
