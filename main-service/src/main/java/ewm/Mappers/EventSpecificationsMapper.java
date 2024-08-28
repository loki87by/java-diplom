package ewm.Mappers;

import ewm.Entityes.Event;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class EventSpecificationsMapper {
    public Specification<Event> filterEvents(List<Long> userIds,
                                             List<String> states,
                                             List<Long> catsIds,
                                             Timestamp minTime,
                                             Timestamp maxTime) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (userIds != null && !userIds.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("userId").in(userIds));
            }

            if (states != null && !states.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("state").in(states));
            }

            if (catsIds != null && !catsIds.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("categoryId").in(catsIds));
            }

            if (minTime != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), minTime));
            }

            if (maxTime != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), maxTime));
            }
            return predicate;
        };
    }

    public Specification<Event> filterEvents(
            String text,
            List<Long> catsIds,
            Boolean paid,
            Timestamp minTime,
            Timestamp maxTime,
            Boolean onlyAvailable,
            String sort) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("state"), "PUBLISHED"));

            if (text != null && !text.isEmpty()) {
                String searchText = "%" + text.toLowerCase() + "%";
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), searchText),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), searchText),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchText)
                ));
            }

            if (catsIds != null && !catsIds.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("CategoryId").in(catsIds));
            }

            if (paid != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paid"), paid));
            }

            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder
                            .greaterThanOrEqualTo(root.get("eventDate"), minTime));

            if (maxTime != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), maxTime));
            }

            if (onlyAvailable != null && onlyAvailable) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThan(root.get("confirmedRequests"), root.get("participantLimit")));
            }

            if (sort != null && !sort.isEmpty()) {

                if (sort.equals("EVENT_DATE")) {
                    query.orderBy(criteriaBuilder.asc(root.get("eventDate")));
                } else if (sort.equals("VIEWS")) {
                    query.orderBy(criteriaBuilder.asc(root.get("views")));
                }
            }

            return predicate;
        };
    }
}
