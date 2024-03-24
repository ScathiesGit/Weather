package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HibernateLocationRepository implements LocationRepository {

    private final HibernateExecutor executor;

    @Override
    public void save(Location location) {
        executor.execute(session -> session.persist(location));
    }

    @Override
    public void delete(Location location) {
        executor.execute(session -> session.remove(location));
    }
}
