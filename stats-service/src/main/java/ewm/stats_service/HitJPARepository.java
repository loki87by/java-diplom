package ewm.stats_service;

import jakarta.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Timestamp;
import java.util.List;

@RepositoryRestResource
public interface HitJPARepository extends JpaRepository<Hit, Long> {

    @Query("SELECT h.app, h.uri, COUNT(distinct(h.ip)) FROM Hit h WHERE h.timestamp BETWEEN :minTime AND :maxTime GROUP BY h.app, h.uri")
    List<Tuple> fetchUniqueStats(@Param("minTime") Timestamp minTime, @Param("maxTime") Timestamp maxTime);

    @Query("SELECT h.app, h.uri, COUNT(distinct(h.ip)) FROM Hit h WHERE h.uri IN :uris AND h.timestamp BETWEEN :minTime AND :maxTime GROUP BY h.app, h.uri")
    List<Tuple> fetchUniqueStatsFrom(@Param("minTime") Timestamp minTime, @Param("maxTime") Timestamp maxTime, @Param("uris") List<String> uris);

    @Query("SELECT h.app, h.uri, COUNT(h) FROM Hit h WHERE h.timestamp BETWEEN :minTime AND :maxTime GROUP BY h.app, h.uri")
    List<Tuple> fetchStats(@Param("minTime") Timestamp minTime, @Param("maxTime") Timestamp maxTime);

    @Query("SELECT h.app, h.uri, COUNT(h) FROM Hit h WHERE h.uri IN :uris AND h.timestamp BETWEEN :minTime AND :maxTime GROUP BY h.app, h.uri")
    List<Tuple> fetchStatsFrom(@Param("minTime") Timestamp minTime, @Param("maxTime") Timestamp maxTime, @Param("uris") List<String> uris);

}
