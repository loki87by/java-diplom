package ewm.stats_service;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class HitRepository {
    private final HitJPARepository jpa;

    public String setHit(Hit hit) {
        try {
            jpa.saveAndFlush(hit);
            return "Информация сохранена";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Tuple> findIn(Timestamp minTime,
                              Timestamp maxTime,
                              List<String> uris,
                              boolean unique) {

        if (unique) {
            return jpa.fetchUniqueStatsFrom(minTime, maxTime, uris);
        } else {
            return jpa.fetchStatsFrom(minTime, maxTime, uris);
        }
    }

    public List<Tuple> findAll(Timestamp minTime,
                               Timestamp maxTime,
                               boolean unique) {

        if (unique) {
            return jpa.fetchUniqueStats(minTime, maxTime);
        } else {
            return jpa.fetchStats(minTime, maxTime);
        }
    }

}
