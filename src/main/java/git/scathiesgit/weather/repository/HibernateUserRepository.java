package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final HibernateExecutor executor;

    @Override
    public User save(User user) {
        executor.execute(session -> session.persist(user));
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return ofNullable(executor.executeQuery(
                session -> {
                    var loadedUser = session.get(User.class, id);
                    Hibernate.initialize(loadedUser.getLocations());
                    return loadedUser;
                }
        ));
    }

    @Override
    public Optional<User> findByUsername(java.lang.String username) {
        return ofNullable(executor.executeQuery(session -> {
            User user = session.createQuery("from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            Hibernate.initialize(user.getLocations());
            return user;
        }));
    }

    @Override
    public void saveUserLocation(User user, Location location) {
        executor.execute(session -> {

            var loadedUser = session.get(User.class, user.getId());
            var loadedLocation = session.createQuery(
                            "from Location l where lat = :lat and lon = :lon", Location.class)
                    .setParameter("lat", location.getLat())
                    .setParameter("lon", location.getLon())
                    .uniqueResult();

            if (loadedLocation == null) {
                session.persist(location);
                loadedUser.addLocation(location);
            } else {
                loadedUser.addLocation(loadedLocation);
            }
        });
    }

    @Override
    public void deleteUserLocation(User user, Location location) {
        executor.execute(session -> {
            var loadedUser = session.get(User.class, user.getId());
            loadedUser.getLocations().remove(location);
        });
    }
}
