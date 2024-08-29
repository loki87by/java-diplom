
package ewm.stats_service;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class Controller {
    private final MyService service;
    private final Mapper mapper;

    //PRIVATE
    @GetMapping("/stats")
    public List<Stat> getStat(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique
    ) {
        Timestamp from = new Mapper().stringToTimestamp(start);
        Timestamp to = new Mapper().stringToTimestamp(end);
        List<String> urls = new ArrayList<>();

        if(uris != null && !uris.isEmpty()) {
            urls = uris;
        }
        List<Tuple> results =  service.getStatistic(from, to, urls, unique);
        List<Stat> statistic = new ArrayList<>();

        for (Tuple tuple : results) {
            String app = tuple.get(0, String.class);
            String uri = tuple.get(1, String.class);
            Long count = tuple.get(2, Long.class);
            Stat stat = new Stat(app, uri, count);
            statistic.add(stat);
        }
        return statistic;
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public String setHit(
            @RequestBody HitDto dto) {
        Hit hit = mapper.dtoToHit(dto);
        return service.addHit(hit);
    }
}