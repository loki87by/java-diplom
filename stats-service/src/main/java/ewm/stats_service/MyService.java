package ewm.stats_service;

import jakarta.persistence.Tuple;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {
    private final HitRepository repo;

    public String addHit(Hit hit) {
        return repo.setHit(hit);
    }

    public List<Tuple> getStatistic(Timestamp minTime,
                                    Timestamp maxTime,
                                    List<String> uris,
                                    boolean unique) {

        if (!uris.isEmpty()) {
            return repo.findIn(minTime,
                    maxTime,
                    uris,
                    unique);
        } else {
            return repo.findAll(minTime,
                    maxTime,
                    unique);
        }
    }

}
