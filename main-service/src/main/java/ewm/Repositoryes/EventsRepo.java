package ewm.Repositoryes;

import ewm.Mappers.EventSpecificationsMapper;
import ewm.Entityes.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventsRepo {
    private final EventJPARepository jpa;
    private final EventSpecificationsMapper esMapper;

    public List<Event> getEvents(Long id) {
        return jpa.findByCategoryId(id).orElse(null);
    }

    public List<Event> getById(Long userId, int size, int from) {
        Pageable pageable = PageRequest.of(from, size);
        return jpa.findAllByUserId(userId, pageable).getContent();
    }

    public Event setEvent(Event event) {
        return jpa.saveAndFlush(event);
    }

    public Event getEvent(Long eventId) {
        return jpa.findById(eventId).orElse(null);
    }

    public Event getEvent(Long userId, Long eventId) {
        return jpa.findByIdAndUserId(userId, eventId).orElse(null);
    }

    public List<Event> findEvents(List<Long> users,
                                  List<Long> categories,
                                  List<String> states,
                                  String rangeStart,
                                  String rangeEnd,
                                  int from,
                                  int size) {
        Timestamp minTime = Timestamp.from(Instant.now());

        if (rangeStart != null) {

            if(rangeStart.length() <= 11) {
                rangeStart = rangeStart.trim()+" 00:00:00";
            }
            minTime = Timestamp.valueOf(rangeStart);
        }
        Timestamp maxTime = null;

        if (rangeEnd != null) {

            if(rangeEnd.length() <= 11) {
                rangeEnd = rangeEnd.trim()+" 23:59:59";
            }
            maxTime = Timestamp.valueOf(rangeEnd);
        }
        Specification<Event> spec = esMapper.filterEvents(users, states, categories, minTime, maxTime);
        Pageable pageable = PageRequest.of(from, size);
        return jpa.findAll(spec, pageable);
    }

    public List<Event> findEvents(String text,
                                  List<Long> catsIds,
                                  Boolean paid,
                                  String rangeStart,
                                  String rangeEnd,
                                  Boolean onlyAvailable,
                                  String sort,
                                  int from,
                                  int size) {
        Timestamp minTime;
        if (rangeStart != null) {

            if(rangeStart.length() <= 11) {
                rangeStart = rangeStart.trim()+" 00:00:00";
            }
            minTime = Timestamp.valueOf(rangeStart);
        } else {
            minTime = Timestamp.from(Instant.now());
        }
        Timestamp maxTime;
        if (rangeEnd != null) {

            if(rangeEnd.length() <= 11) {
                rangeEnd = rangeEnd.trim()+" 23:59:59";
            }
            maxTime = Timestamp.valueOf(rangeEnd);
        } else {
            maxTime = null;
        }
        Specification<Event> spec = esMapper.filterEvents(text,
                catsIds,
                paid,
                minTime,
                maxTime,
                onlyAvailable,
                sort);
        Pageable pageable = PageRequest.of(from, size);
        return jpa.findAll(spec, pageable);
    }

    public List<Event> findAllByCategoryId(Long id) {
        return jpa.findAllByCategoryId(id);
    }
}
