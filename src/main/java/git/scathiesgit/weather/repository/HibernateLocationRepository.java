package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.repository.exception.LocationAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HibernateLocationRepository implements LocationRepository {

    private final HibernateExecutor executor;

    @Override
    public void save(Location location) {
        try {
            executor.execute(session -> session.persist(location));
        } catch (Exception e) {
            throw new LocationAlreadyExistsException(e);
        }
    }

    @Override
    public void delete(Location location) {
        executor.execute(session -> session
                .createQuery("DELETE FROM Location l " +
                        "WHERE l.lat = :lat and l.lon = :lon and l.userId = :userId")
                .setParameter("lat", location.getLat())
                .setParameter("lon", location.getLon())
                .setParameter("userId", location.getUserId())
                .executeUpdate());
    }
}
