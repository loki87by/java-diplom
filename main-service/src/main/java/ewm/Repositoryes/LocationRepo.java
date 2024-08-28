package ewm.Repositoryes;

import ewm.Entityes.Location;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepo {
    private final LocationJPARepository jpa;

    public Location findById(Long id) {
        return jpa.findById(id).orElse(null);
    }

    public Optional<Location> findByData(double lat, double lon) {
        return jpa.findLocationByLatAndLon(lat, lon);
    }

    public Location setLocation(Location location) {
        return findByData(location.getLat(), location.getLon()).orElseGet(() -> jpa.saveAndFlush(location));
    }

}
