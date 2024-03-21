package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(java.lang.String username);

    void saveUserLocation(User user, Location location);

    void deleteUserLocation(User user, Location location);
}
