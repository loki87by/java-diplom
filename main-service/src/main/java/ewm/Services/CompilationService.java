package ewm.Services;

import ewm.Dtos.EventDto;
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

        if (!repo.checkUniqueTitle(title)) {
            throw new ConflictException("could not execute statement; SQL [n/a]; constraint [uq_compilation_name];");
        }
        Compilation comp = repo.setEmptyCompilation(pinned, title);

        if (eventsIds == null || eventsIds.isEmpty()) {
            return comp;
        } else {
            List<EventDto> events = new ArrayList<>();

            for (Long id : eventsIds) {
                Event event = eRepo.getEvent(id);

                if(event != null) {
                    event.setCategoryId(comp.getId());
                    events.add(mapper.toObject(event));
                }
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

        if (!repo.checkUniqueTitle(comp.getTitle())) {
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
        List<Event> events = eRepo.findAllByCategoryId(id);

        for (Event event : events) {
            Long eventId = event.getId();

            if (!eventIds.contains(eventId)) {
                event.setCategoryId(null);
                eRepo.setEvent(event);
            }
        }

        for (Long currentId : eventIds) {
            List<Event> currEvents = events
                    .stream()
                    .filter(event -> event.getCompilationId().equals(currentId))
                    .toList();

            if(!currEvents.isEmpty()) {
                Event currEvent = eRepo.getEvent(currentId);

                if(currEvent != null) {
                    currEvent.setCompilationId(id);
                    eRepo.setEvent(currEvent);
                }
            }
        }
        return repo.getCurrent(id);
    }
}
